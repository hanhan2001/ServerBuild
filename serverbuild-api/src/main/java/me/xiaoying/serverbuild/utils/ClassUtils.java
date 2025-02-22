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

        if (clazz == int.class)
            descriptor = "I";
        else if (clazz == long.class)
            descriptor = "J";
        else if (clazz == float.class)
            descriptor = "F";
        else if (clazz == double.class)
            descriptor = "D";
        else if (clazz == boolean.class)
            descriptor = "Z";
        else if (clazz == char.class)
            descriptor = "C";
        else if (clazz == byte.class)
            descriptor = "B";
        else if (clazz == short.class)
            descriptor = "S";
        else if (clazz == void.class)
            descriptor = "V";
        else
            descriptor = "L" + clazz.getName().replace('.', '/') + ";";

        return descriptor;
    }

    public static int getReturnByObject(Object object) {
        return ClassUtils.getReturnByClass(object.getClass());
    }

    public static int getReturnByClass(Class<?> clazz) {
        if (clazz == void.class)
            return Opcodes.RETURN;
        else if (clazz == int.class || clazz == byte.class || clazz == short.class || clazz == char.class || clazz == boolean.class)
            return Opcodes.IRETURN;
        else if (clazz == long.class)
            return Opcodes.LRETURN;
        else if (clazz == float.class)
            return Opcodes.FRETURN;
        else if (clazz == double.class)
            return Opcodes.DRETURN;
        else
            return Opcodes.ARETURN;
    }
}