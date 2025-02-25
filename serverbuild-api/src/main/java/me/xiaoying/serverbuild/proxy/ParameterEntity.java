package me.xiaoying.serverbuild.proxy;

import net.bytebuddy.jar.asm.Opcodes;

public class ParameterEntity {
    private final int index;
    private final String truthClass;
    private final Class<?> type;

    public ParameterEntity(int index, String truthClass, Class<?> type) {
        this.index = index;
        this.truthClass = truthClass;
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public String getTruthClass() {
        return this.truthClass;
    }

    public Class<?> getType() {
        return this.type;
    }

    public int getReturnOpcodes() {
        if (this.getType() == void.class || this.getType() == Void.class)
            return Opcodes.RETURN;
        else if (this.getType() == int.class || this.getType() == Integer.class ||
                this.getType() == byte.class || this.getType() == Byte.class ||
                this.getType() == short.class || this.getType() == Short.class ||
                this.getType() == char.class || this.getType() == Character.class ||
                this.getType() == boolean.class || this.getType() == Boolean.class)
            return Opcodes.IRETURN;
        else if (this.getType() == long.class || this.getType() == Long.class)
            return Opcodes.LRETURN;
        else if (this.getType() == float.class || this.getType() == Float.class)
            return Opcodes.FRETURN;
        else if (this.getType() == double.class || this.getType() == Double.class)
            return Opcodes.DRETURN;

        return Opcodes.ARETURN;
    }

    public int getLoadOpcodes() {
        if (this.getType() == int.class || this.getType() == Integer.class ||
                this.getType() == byte.class || this.getType() == Byte.class ||
                this.getType() == short.class || this.getType() == Short.class ||
                this.getType() == char.class || this.getType() == Character.class ||
                this.getType() == boolean.class || this.getType() == Boolean.class)
            return Opcodes.ILOAD;
        if (this.getType() == long.class || this.getType() == Long.class)
            return Opcodes.LLOAD;
        if (this.getType() == float.class || this.getType() == Float.class)
            return Opcodes.FLOAD;

        if (this.getType() == int[].class || this.getType() == Integer[].class)
            return Opcodes.IALOAD;
        if (this.getType() == long[].class || this.getType() == Long[].class)
            return Opcodes.LALOAD;
        if (this.getType() == float[].class || this.getType() == Float[].class)
            return Opcodes.FALOAD;
        if (this.getType() == double[].class || this.getType() == Double[].class)
            return Opcodes.DALOAD;
        if (this.getType() == byte[].class || this.getType() == Byte[].class || this.getType() == boolean[].class || this.getType() == Boolean[].class)
            return Opcodes.BALOAD;
        if (this.getType() == short[].class || this.getType() == Short[].class)
            return Opcodes.SALOAD;
        if (this.getType() == char[].class || this.getType() == Character[].class)
            return Opcodes.CALOAD;
        return Opcodes.ALOAD;
    }
}