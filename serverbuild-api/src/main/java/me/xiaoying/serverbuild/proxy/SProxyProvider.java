package me.xiaoying.serverbuild.proxy;

import me.xiaoying.serverbuild.proxy.annotation.*;
import me.xiaoying.serverbuild.utils.ClassUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatchers;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class SProxyProvider {
    private final String version;
    private boolean debug = false;
    private File outFile = new File(System.getProperty("user.dir"), "debug");

    public SProxyProvider() {
        String version = Bukkit.getServer().getBukkitVersion();

        String[] splitByPoint = version.split("\\.");
        String[] splitByBar = version.split("-");

        String prefix = splitByPoint[0] + "_" + splitByPoint[1];
        String suffix = splitByBar[1];

        if (suffix.contains("."))
            suffix = String.valueOf(suffix.charAt(0)) + suffix.charAt(3);

        this.version = "v" + prefix + "_" + suffix;
    }

    public SProxyProvider(String version) {
        this.version = version;
    }

    /**
     * Set proxy debug mode
     *
     * @param enable on / off
     * @return SProxyProvider
     */
    public SProxyProvider debug(boolean enable) {
        this.debug = enable;
        return this;
    }

    /**
     * Set proxy out classes' path
     *
     * @param file save folder
     * @return SProxyProvider
     */
    public SProxyProvider setOutFile(File file) {
        this.outFile = file;
        return this;
    }

    public <T extends SProxy> T constructorClass(Class<T> clazz, Object instance) throws Exception {
        if (!SProxy.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException("class of " + clazz.getName() + " need to extended " + SProxy.class.getName());

        SClass classAnnotation = clazz.getAnnotation(SClass.class);
        if (classAnnotation == null)
            throw new IllegalArgumentException("class of " + clazz.getName() + " need @SClass annotation.");

        // get target class
        Class<?> target = null;
        for (String s : classAnnotation.className()) {
            String version = this.version;

            // split special format
            if (s.contains("-")) {
                String[] split = s.replace(" ", "").split("-");
                // net.minecraft.network - ?version?
                // contains - but haven't set version
                if (split.length == 1)
                    continue;

                s = split[0];
                version = split[1];
            }

            if (classAnnotation.type().getClassName(s, version).isEmpty())
                continue;

            try {
                target = this.getClass().getClassLoader().loadClass(classAnnotation.type().getClassName(s, version));
                break;
            } catch (ClassNotFoundException e) {}
        }

        // byte buddy
        DynamicType.Builder<T> subclass = new ByteBuddy().subclass(clazz);

        subclass = subclass.defineField("instance", Object.class, Modifier.PRIVATE);
        subclass = subclass.defineMethod("setInstance", void.class, Modifier.PUBLIC)
                .withParameters(Object.class)
                .intercept(new Implementation.Simple((methodVisitor, context, methodDescription) -> {
                    methodVisitor.visitCode();

                    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                    methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);

                    methodVisitor.visitFieldInsn(Opcodes.PUTFIELD,
                            context.getInstrumentedType().asErasure().getName().replace(".", "/"),
                            "instance",
                            "Ljava/lang/Object;");

                    methodVisitor.visitInsn(Opcodes.RETURN);

                    methodVisitor.visitMaxs(2, 2);
                    methodVisitor.visitEnd();
                    return new ByteCodeAppender.Size(2, 2);
                }));

        subclass = subclass.defineMethod("getInstance", Object.class, Modifier.PUBLIC).intercept(new Implementation.Simple((methodVisitor, context, methodDescription) -> {
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

            methodVisitor.visitFieldInsn(Opcodes.GETFIELD,
                    context.getInstrumentedType().asErasure().getName().replace(".", "/"),
                    "instance",
                    "Ljava/lang/Object;");

            // 返回获取到的值
            methodVisitor.visitInsn(Opcodes.ARETURN);

            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
            return new ByteCodeAppender.Size(1, 1);
        }));

        // methods
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.getAnnotation(SConstructor.class) != null)
                subclass = this.setConstructorMethod(subclass, declaredMethod);

            if (declaredMethod.getAnnotation(SMethod.class) != null) {
                if (instance == null && declaredMethod.getAnnotation(SMethod.class).needInstance())
                    continue;

                subclass = this.setMethod(subclass, declaredMethod, target, instance);
            }
            if (declaredMethod.getAnnotation(SFieldMethod.class) != null) {
                if (instance == null)
                    continue;

                subclass = this.setFiledMethod(subclass, declaredMethod, target);
            }
        }

        DynamicType.Unloaded<T> make = subclass.make();

        // new instance class when finished method handle
        DynamicType.Loaded<T> load = make.load(clazz.getClassLoader());
        if (this.debug)
            load.saveIn(this.outFile);
        T t = load.getLoaded().newInstance();
        t.getClass().getDeclaredMethod("setInstance", Object.class).invoke(t, instance);

        // filed handle
        for (Field declaredField : t.getClass().getSuperclass().getDeclaredFields())
            this.setFiled(t, declaredField, t, target);

        return t;
    }

    private <T> DynamicType.Builder<T> setMethod(DynamicType.Builder<T> subclass, Method method, Class<?> target, Object instance) throws Exception {
        SMethod annotation = method.getAnnotation(SMethod.class);

        if (annotation == null)
            return subclass;

        Map<Integer, ParameterEntity> parameters = new HashMap<>();
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter parameter = method.getParameters()[i];
            SParameter anno = parameter.getAnnotation(SParameter.class);

            if (anno == null)
                continue;

            parameters.put(anno.index(), new ParameterEntity(anno.index(), i + 1, anno.truthClass(), parameter.getType()));
        }

        for (int i = 0; i < parameters.size(); i++) {
            if (parameters.get(i) != null)
                continue;

            if (i + 1 >= parameters.size())
                break;

            for (int j = i; j < parameters.size(); j++) {
                if (parameters.get(i) == null)
                    continue;

                throw new RuntimeException("missing parameter " + i + " index in " + subclass.getClass().getSuperclass().getName() + method.getName());
            }
        }

        DynamicType.Builder.MethodDefinition.ImplementationDefinition<T> method1 = subclass.method(ElementMatchers.named(method.getName()));

        subclass = method1.intercept(new Implementation.Simple((methodVisitor, context, methodDescription) -> {
            methodVisitor.visitCode();

            methodVisitor.visitLdcInsn(target.getName());
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);

            methodVisitor.visitLdcInsn(annotation.methodName());

            if (parameters.size() < 6)
                methodVisitor.visitInsn(Opcodes.ICONST_0 + parameters.size());
            else
                methodVisitor.visitIntInsn(Opcodes.BIPUSH, parameters.size());

            methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Class");

            for (Map.Entry<Integer, ParameterEntity> entry : parameters.entrySet()) {
                methodVisitor.visitInsn(Opcodes.DUP);
                methodVisitor.visitIntInsn(Opcodes.BIPUSH, entry.getKey());

                if (!entry.getValue().isPrimitive()) {
                    if (entry.getValue().getTruthClass() != null && !entry.getValue().getTruthClass().isEmpty())
                        methodVisitor.visitLdcInsn(entry.getValue().getTruthClass());
                    else
                        methodVisitor.visitLdcInsn(entry.getValue().getType().getName());

                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
                } else {
                    if (entry.getValue().getTypeExact() == int.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
                    else if (entry.getValue().getTypeExact() == long.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
                    else if (entry.getValue().getTypeExact() == float.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
                    else if (entry.getValue().getTypeExact() == double.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
                    else if (entry.getValue().getTypeExact() == boolean.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
                    else if (entry.getValue().getTypeExact() == byte.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
                    else if (entry.getValue().getTypeExact() == short.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
                    else if (entry.getValue().getTypeExact() == char.class)
                        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Character", "TYPE", "Ljava/lang/Class;");
                }
                methodVisitor.visitInsn(Opcodes.AASTORE);
            }

            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitVarInsn(Opcodes.ASTORE, parameters.size() + 1);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, parameters.size() + 1);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    "java/lang/reflect/AccessibleObject",
                    "setAccessible",
                    "(Z)V",
                    false);

            methodVisitor.visitVarInsn(Opcodes.ALOAD, parameters.size() + 1);

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

            methodVisitor.visitFieldInsn(Opcodes.GETFIELD,
                    context.getInstrumentedType().asErasure().getName().replace(".", "/"),
                    "instance",
                    "Ljava/lang/Object;");

            if (instance != null)
                methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, instance.getClass().getName().replace('.', '/'));

            methodVisitor.visitIntInsn(Opcodes.BIPUSH, parameters.size());
            methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");

            for (int i = 0; i < parameters.size(); i++) {
                ParameterEntity entity = parameters.get(i);
                methodVisitor.visitInsn(Opcodes.DUP);
                methodVisitor.visitIntInsn(Opcodes.BIPUSH, i);
                methodVisitor.visitVarInsn(parameters.get(i).getLoadOpcodes(), parameters.get(i).getStackIndex());

                switch (entity.getLoadOpcodes()) {
                    case Opcodes.ILOAD:
                        if (entity.getType() == Integer.class)
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                        else if (entity.getType() == Byte.class)
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                        else if (entity.getType() == Short.class)
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                        else if (entity.getType() == Character.class)
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                        else if (entity.getType() == Boolean.class)
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);

                        break;
                    case Opcodes.LLOAD:
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(I)Ljava/lang/Long;", false);
                        break;
                    case Opcodes.FLOAD:
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(I)Ljava/lang/Float;", false);
                        break;
                }

                if (parameters.get(i).getTruthClass() != null && !parameters.get(i).getTruthClass().isEmpty())
                    methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, parameters.get(i).getTruthClass().replace(".", "/"));

                methodVisitor.visitInsn(Opcodes.AASTORE);
            }

            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);

            int stackSize;
            int returnByClass = ClassUtils.getReturnByClass(method.getReturnType());
            if (returnByClass == Opcodes.ARETURN) {
                stackSize = 4;
                String type;
                if (!annotation.returnClass().isEmpty())
                    type = annotation.returnClass().replace(".", "/");
                else
                    type = method.getReturnType().getName().replace(".", "/");
                methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, type);
                methodVisitor.visitInsn(returnByClass);
            } else {
                stackSize = 3;
                methodVisitor.visitInsn(Opcodes.POP);
                methodVisitor.visitInsn(Opcodes.RETURN);
            }
            methodVisitor.visitMaxs(Math.max(parameters.size() + stackSize, 6), parameters.size() + 2);
            methodVisitor.visitEnd();
            return new ByteCodeAppender.Size(Math.max(parameters.size() + stackSize, 6), parameters.size() + 2);
        }));

        return subclass;
    }

    private <T> DynamicType.Builder<T> setConstructorMethod(DynamicType.Builder<T> subclass, Method method) {
        SConstructor annotation = method.getAnnotation(SConstructor.class);

        if (annotation == null)
            return subclass;

        String target = annotation.target();

        Map<Integer, ParameterEntity> parameters = new HashMap<>();
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter parameter = method.getParameters()[i];
            SParameter anno = parameter.getAnnotation(SParameter.class);

            if (anno == null)
                continue;

            parameters.put(anno.index(), new ParameterEntity(anno.index(), i + 1, anno.truthClass(), parameter.getType()));
        }

        for (int i = 0; i < parameters.size(); i++) {
            if (parameters.get(i) != null)
                continue;

            if (i + 1 >= parameters.size())
                break;

            for (int j = i; j < parameters.size(); j++) {
                if (parameters.get(i) == null)
                    continue;

                throw new RuntimeException("missing parameter " + i + " index in " + subclass.getClass().getSuperclass().getName() + method.getName());
            }
        }

        DynamicType.Builder.MethodDefinition.ImplementationDefinition<T> method1 = subclass.method(ElementMatchers.named(method.getName()));

        subclass = method1.intercept(new Implementation.Simple((methodVisitor, context, methodDescription) -> {
            methodVisitor.visitCode();

            methodVisitor.visitTypeInsn(Opcodes.NEW, target.replace('.', '/'));
            methodVisitor.visitInsn(Opcodes.DUP);

            StringBuilder stringClasses = new StringBuilder();
            for (Map.Entry<Integer, ParameterEntity> entry : parameters.entrySet()) {
                stringClasses.append(ClassUtils.getClassByteCodeName(entry.getValue().getTypeExact()));
                methodVisitor.visitVarInsn(entry.getValue().getLoadOpcodes(), entry.getValue().getStackIndex());

                if (entry.getValue().getTruthClass() != null && !entry.getValue().getTruthClass().isEmpty())
                    methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, entry.getValue().getTruthClass().replace(".", "/"));
            }

            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL,
                    target.replace('.', '/'),
                    "<init>",
                    "(" + stringClasses + ")V",
                    false);

            methodVisitor.visitInsn(Opcodes.ARETURN);
            methodVisitor.visitMaxs(4, 3);
            methodVisitor.visitEnd();
            return new ByteCodeAppender.Size(4, 3);
        }));
        return subclass;
    }

    private <T> DynamicType.Builder<T> setFiledMethod(DynamicType.Builder<T> subclass, Method method, Class<?> target) throws Exception {
        SFieldMethod filedAnnotation = method.getAnnotation(SFieldMethod.class);
        if (filedAnnotation == null)
            return subclass;

        String name = filedAnnotation.fieldName();

        DynamicType.Builder.MethodDefinition.ImplementationDefinition<T> method1 = subclass.method(ElementMatchers.named(method.getName()));

        Field declaredField = target.getDeclaredField(name);
        declaredField.setAccessible(true);

        subclass = method1.intercept(new Implementation.Simple((methodVisitor, context, methodDescription) -> {
            methodVisitor.visitCode();

            methodVisitor.visitLdcInsn(target.getName());
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);

            methodVisitor.visitLdcInsn(filedAnnotation.fieldName());

            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredField", "(Ljava/lang/String;)Ljava/lang/reflect/Field;", false);
            methodVisitor.visitVarInsn(Opcodes.ASTORE, 2);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 2);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    "java/lang/reflect/AccessibleObject",
                    "setAccessible",
                    "(Z)V",
                    false);

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 2);

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

            methodVisitor.visitFieldInsn(Opcodes.GETFIELD,
                    context.getInstrumentedType().asErasure().getName().replace(".", "/"),
                    "instance",
                    "Ljava/lang/Object;");

            // getter
            if (filedAnnotation.type() == SFieldMethod.Type.GETTER) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Field", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
                methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, method.getReturnType().getName().replace(".", "/"));

                methodVisitor.visitInsn(ClassUtils.getReturnByClass(declaredField.getType()));
                methodVisitor.visitMaxs(3, 3);
                methodVisitor.visitEnd();
                return new ByteCodeAppender.Size(3, 3);
            }

            // setter
            methodVisitor.visitVarInsn(ClassUtils.getLoadOpcode(method.getParameters()[0].getType()), 1);

            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Field", "set", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(3, 3);
            methodVisitor.visitEnd();
            return new ByteCodeAppender.Size(3, 3);
        }));
        return subclass;
    }

    private <T> Object setFiled(T t, Field field, T newClass, Class<?> target) throws Exception {

        SField sfield = field.getAnnotation(SField.class);

        if (sfield == null)
            return t;

        boolean supported = false;
        for (String s : sfield.version()) {
            if (!this.version.equalsIgnoreCase(s))
                continue;

            supported = true;
            break;
        }

        if (!supported)
            return t;

        field.setAccessible(true);
        Field targetField = target.getDeclaredField(sfield.fieldName());
        targetField.setAccessible(true);

        Field declaredField = newClass.getClass().getDeclaredField("instance");
        declaredField.setAccessible(true);
        Object object = declaredField.get(newClass);

        field.set(t, field.getType().cast(targetField.get(object)));
        return t;
    }
}