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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class SProxyProvider {
    private final String version;

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

    public <T extends SProxy> T constructorClass(Class<T> clazz, Object instance) throws Exception {
        if (!SProxy.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException("class of " + clazz.getName() + " need to extended " + SProxy.class.getName());

        SClass classAnnotation = clazz.getAnnotation(SClass.class);
        if (classAnnotation == null)
            throw new IllegalArgumentException("class of " + clazz.getName() + " need @SClass annotation.");

        // get target class
        Class<?> target;
        if (classAnnotation.type().getClassName(classAnnotation.className(), this.version).isEmpty())
            target = null;
        else
            target = this.getClass().getClassLoader().loadClass(classAnnotation.type().getClassName(classAnnotation.className(), this.version));

        // byte buddy
        DynamicType.Builder<T> subclass = new ByteBuddy().subclass(clazz);

        subclass = subclass.defineField("temporary", Object.class, Modifier.PRIVATE);
        subclass = subclass.defineMethod("setTemporary", void.class, Modifier.PUBLIC)
                .withParameters(Object.class)
                .intercept(new Implementation.Simple((methodVisitor, context, methodDescription) -> {
                    methodVisitor.visitCode();

                    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                    methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);

                    methodVisitor.visitFieldInsn(Opcodes.PUTFIELD,
                            context.getInstrumentedType().asErasure().getName().replace(".", "/"),
                            "temporary",
                            "Ljava/lang/Object;");

                    methodVisitor.visitInsn(Opcodes.RETURN);

                    methodVisitor.visitMaxs(2, 2);
                    methodVisitor.visitEnd();
                    return new ByteCodeAppender.Size(2, 2);
                }));

        // methods
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.getAnnotation(SConstructor.class) != null)
                subclass = this.setConstructorMethod(subclass, declaredMethod);
            else if (declaredMethod.getAnnotation(SMethod.class) != null)
                subclass = this.setMethod(subclass, declaredMethod, target, instance);
            else if (declaredMethod.getAnnotation(SFieldMethod.class) != null)
                subclass = this.setFiledMethod(subclass, declaredMethod, target, instance);
        }

        DynamicType.Unloaded<T> make = subclass.make();

        // new instance class when finished method handle
        DynamicType.Loaded<T> load = make.load(clazz.getClassLoader());
        T t = load.getLoaded().newInstance();
        t.getClass().getDeclaredMethod("setTemporary", Object.class).invoke(t, instance);

        // filed handle
        for (Field declaredField : t.getClass().getSuperclass().getDeclaredFields())
            this.setFiled(t, declaredField, t, target);

        return t;
    }

    private <T> DynamicType.Builder<T> setMethod(DynamicType.Builder<T> subclass, Method method, Class<?> target, Object instance) throws Exception {
        SMethod annotation = method.getAnnotation(SMethod.class);

        if (annotation == null)
            return subclass;

        Map<Integer, Integer> map = new HashMap<>();
        Class<?>[] classes = new Class<?>[method.getParameters().length];
        Map<Integer, String> truthMap = new HashMap<>();
        for (Parameter parameter : method.getParameters()) {
            SParameter anno = parameter.getAnnotation(SParameter.class);

            if (anno == null)
                continue;

            classes[anno.index()] = parameter.getType();
            truthMap.put(map.size(), anno.truthClass());
            map.put(map.size(), anno.index());
        }

        for (int i = 0; i < classes.length; i++) {
            if (classes[i] != null)
                continue;

            if (i + 1 >= classes.length)
                break;

            for (int j = i; j < classes.length; j++) {
                if (classes[j] == null)
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

            if (classes.length < 6)
                methodVisitor.visitInsn(Opcodes.ICONST_0 + classes.length);
            else
                methodVisitor.visitIntInsn(Opcodes.BIPUSH, classes.length);

            methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Class");

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int index = entry.getKey();
                int paramIndex = entry.getValue();
                methodVisitor.visitInsn(Opcodes.DUP);
                methodVisitor.visitIntInsn(Opcodes.BIPUSH, index);

                if (truthMap.get(entry.getKey()) != null && !truthMap.get(entry.getKey()).isEmpty())
                    methodVisitor.visitLdcInsn(truthMap.get(entry.getKey()));
                else
                    methodVisitor.visitLdcInsn(classes[paramIndex].getName());

                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
                methodVisitor.visitInsn(Opcodes.AASTORE);
            }

            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitVarInsn(Opcodes.ASTORE, classes.length + 1);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, classes.length + 1);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    "java/lang/reflect/AccessibleObject",
                    "setAccessible",
                    "(Z)V",
                    false);

            methodVisitor.visitVarInsn(Opcodes.ALOAD, classes.length + 1);

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

            methodVisitor.visitFieldInsn(Opcodes.GETFIELD,
                    context.getInstrumentedType().asErasure().getName().replace(".", "/"),
                    "temporary",
                    "Ljava/lang/Object;");

            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, instance.getClass().getName().replace('.', '/'));

            methodVisitor.visitIntInsn(Opcodes.BIPUSH, map.size());
            methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");

            for (int i = 0; i < map.size(); i++) {
                methodVisitor.visitInsn(Opcodes.DUP);
                methodVisitor.visitIntInsn(Opcodes.BIPUSH, i);
                methodVisitor.visitVarInsn(Opcodes.ALOAD, map.get(i) + 1);
                if (truthMap.get(i) != null && !truthMap.get(i).isEmpty())
                    methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, truthMap.get(i).replace(".", "/"));

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
            methodVisitor.visitMaxs(Math.max(map.size() + stackSize, 6), classes.length + 2);
            methodVisitor.visitEnd();
            return new ByteCodeAppender.Size(Math.max(map.size() + stackSize, 6), classes.length + 2);
        }));

        return subclass;
    }

    private <T> DynamicType.Builder<T> setConstructorMethod(DynamicType.Builder<T> subclass, Method method) {
        SConstructor annotation = method.getAnnotation(SConstructor.class);

        if (annotation == null)
            return subclass;

        String target = annotation.target();

        Map<Integer, Integer> map = new HashMap<>();
        Class<?>[] classes = new Class<?>[method.getParameters().length];
        Map<Integer, String> truthMap = new HashMap<>();
        for (Parameter parameter : method.getParameters()) {
            SParameter anno = parameter.getAnnotation(SParameter.class);

            if (anno == null)
                continue;

            classes[anno.index()] = parameter.getType();
            map.put(map.size(), anno.index());
            truthMap.put(map.size(), anno.truthClass());
        }

        for (int i = 0; i < classes.length; i++) {
            if (classes[i] != null)
                continue;

            if (i + 1 >= classes.length)
                break;

            for (int j = i; j < classes.length; j++) {
                if (classes[j] == null)
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
            map.keySet().forEach(index -> {
                stringClasses.append(ClassUtils.getClassByteCodeName(classes[index]));
                methodVisitor.visitVarInsn(Opcodes.ALOAD, map.get(index) + 1);

                if (truthMap.get(index) != null && !truthMap.get(index).isEmpty())
                    methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, truthMap.get(index).replace(".", "/"));
            });

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

    private <T> DynamicType.Builder<T> setFiledMethod(DynamicType.Builder<T> subclass, Method method, Class<?> target, Object instance) throws Exception {
        SFieldMethod filedAnnotation = method.getAnnotation(SFieldMethod.class);
        if (filedAnnotation == null)
            return subclass;

        String name = filedAnnotation.filedName();

        DynamicType.Builder.MethodDefinition.ImplementationDefinition<T> method1 = subclass.method(ElementMatchers.named(method.getName()));

        Field declaredField = target.getDeclaredField(name);
        declaredField.setAccessible(true);

        subclass = method1.intercept(new Implementation.Simple((methodVisitor, context, methodDescription) -> {
            methodVisitor.visitCode();

            methodVisitor.visitLdcInsn(target.getName());
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);

            methodVisitor.visitLdcInsn(filedAnnotation.filedName());

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
                    "temporary",
                    "Ljava/lang/Object;");

            // getter
            if (filedAnnotation.type() == SFieldMethod.Type.GETTER) {

                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Field", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
                methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, method.getReturnType().getName().replace(".", "/"));

                methodVisitor.visitInsn(ClassUtils.getReturnByClass(declaredField.getType()));
                methodVisitor.visitMaxs(3, 3);
                methodVisitor.visitEnd();
                return new ByteCodeAppender.Size(3, 3);
            }

            // setter
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);

            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Field", "set", "(Ljava/lang/Object;Ljava/lang/Object;)V");
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

        field.setAccessible(true);
        Field targetField = target.getDeclaredField(sfield.fieldName());
        targetField.setAccessible(true);

        if (targetField.getType() != field.getType())
            throw new IllegalArgumentException("target field " + targetField.getType() + " not equals " + field.getType());

        Field declaredField = newClass.getClass().getDeclaredField("temporary");
        declaredField.setAccessible(true);
        Object object = declaredField.get(newClass);

        field.set(t, targetField.get(object));
        return t;
    }
}