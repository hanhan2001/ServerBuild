package me.xiaoying.serverbuild.utils;

import net.bytebuddy.jar.asm.Opcodes;

public class ClassUtils {
    /**
     * 获取 class 的字节码格式
     *
     * @param clazz class 文件
     * @return class 的字节码格式
     */
    public static String getClassByteCodeName(Class<?> clazz) {
        String descriptor;

        if (clazz == int.class || clazz == Integer.class)
            descriptor = "I";
        else if (clazz == long.class || clazz == Long.class)
            descriptor = "J";
        else if (clazz == float.class || clazz == Float.class)
            descriptor = "F";
        else if (clazz == double.class || clazz == Double.class)
            descriptor = "D";
        else if (clazz == boolean.class || clazz == Boolean.class)
            descriptor = "Z";
        else if (clazz == char.class || clazz == Character.class)
            descriptor = "C";
        else if (clazz == byte.class || clazz == Byte.class)
            descriptor = "B";
        else if (clazz == short.class || clazz == Short.class)
            descriptor = "S";
        else if (clazz == void.class || clazz == Void.class)
            descriptor = "V";
        else
            descriptor = "L" + clazz.getName().replace('.', '/') + ";";

        return descriptor;
    }

    public static String getClassByteCodeName(String name) {
        try {
            return ClassUtils.getClassByteCodeName(Class.forName(name));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getReturnByObject(Object object) {
        return ClassUtils.getReturnByClass(object.getClass());
    }

    public static int getReturnByClass(Class<?> clazz) {
        if (clazz == void.class || clazz == Void.class)
            return Opcodes.RETURN;
        else if (clazz == int.class || clazz == Integer.class ||
                clazz == byte.class || clazz == Byte.class ||
                clazz == short.class || clazz == Short.class ||
                clazz == char.class || clazz == Character.class ||
                clazz == boolean.class || clazz == Boolean.class)
            return Opcodes.IRETURN;
        else if (clazz == long.class || clazz == Long.class)
            return Opcodes.LRETURN;
        else if (clazz == float.class || clazz == Float.class)
            return Opcodes.FRETURN;
        else if (clazz == double.class || clazz == Double.class)
            return Opcodes.DRETURN;

        return Opcodes.ARETURN;
    }
    
    public static int getLoadOpcode(Class<?> clazz) {
        if (clazz == int.class || clazz == Integer.class ||
                clazz == byte.class || clazz == Byte.class ||
                clazz == short.class || clazz == Short.class ||
                clazz == char.class || clazz == Character.class ||
                clazz == boolean.class || clazz == Boolean.class)
            return Opcodes.ILOAD;
        if (clazz == long.class || clazz == Long.class)
            return Opcodes.LLOAD;
        if (clazz == float.class || clazz == Float.class)
            return Opcodes.FLOAD;

        if (clazz == int[].class || clazz == Integer[].class)
            return Opcodes.IALOAD;
        if (clazz == long[].class || clazz == Long[].class)
            return Opcodes.LALOAD;
        if (clazz == float[].class || clazz == Float[].class)
            return Opcodes.FALOAD;
        if (clazz == double[].class || clazz == Double[].class)
            return Opcodes.DALOAD;
        if (clazz == byte[].class || clazz == Byte[].class || clazz == boolean[].class || clazz == Boolean[].class)
            return Opcodes.BALOAD;
        if (clazz == short[].class || clazz == Short[].class)
            return Opcodes.SALOAD;
        if (clazz == char[].class || clazz == Character[].class)
            return Opcodes.CALOAD;
        return Opcodes.ALOAD;
    }
}