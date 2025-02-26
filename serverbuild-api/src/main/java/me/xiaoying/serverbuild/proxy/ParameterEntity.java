package me.xiaoying.serverbuild.proxy;

import net.bytebuddy.jar.asm.Opcodes;

public class ParameterEntity {
    private final int index;
    private final int stackIndex;
    private final String truthClass;
    private final Class<?> type;

    public ParameterEntity(int index, int stackIndex, String truthClass, Class<?> type) {
        this.index = index;
        this.stackIndex = stackIndex;
        this.truthClass = truthClass;
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public int getStackIndex() {
        return this.stackIndex;
    }

    public String getTruthClass() {
        return this.truthClass;
    }

    public Class<?> getType() {
        if (this.type == int.class)
            return Integer.class;
        if (this.type == short.class)
            return Short.class;
        if (this.type == boolean.class)
            return Boolean.class;
        if (this.type == byte.class)
            return Byte.class;
        if (this.type == char.class)
            return Character.class;
        if (this.type == long.class)
            return Long.class;
        if (this.type == float.class)
            return Float.class;
        if (this.type == double.class)
            return Double.class;
        if (this.type == void.class)
            return Void.class;

        if (this.type == int[].class)
            return Integer[].class;
        if (this.type == short[].class)
            return Short[].class;
        if (this.type == boolean[].class)
            return Boolean[].class;
        if (this.type == byte[].class)
            return Byte[].class;
        if (this.type == char[].class)
            return Character[].class;
        if (this.type == long[].class)
            return Long[].class;
        if (this.type == float[].class)
            return Float[].class;
        if (this.type == double[].class)
            return Double[].class;
        return this.type;
    }

    public Class<?> getTypeExact() {
        return this.type;
    }

    public boolean isPrimitive() {
        return this.type == int.class ||
                this.type == short.class ||
                this.type == boolean.class ||
                this.type == byte.class ||
                this.type == char.class ||
                this.type == long.class ||
                this.type == float.class ||
                this.type == double.class;
    }

    public int getReturnOpcodes() {
        if (this.getType() == Void.class)
            return Opcodes.RETURN;
        else if (this.getType() == Integer.class || this.getType() == Byte.class || this.getType() == Short.class || this.getType() == Character.class || this.getType() == Boolean.class)
            return Opcodes.IRETURN;
        else if (this.getType() == Long.class)
            return Opcodes.LRETURN;
        else if (this.getType() == Float.class)
            return Opcodes.FRETURN;
        else if (this.getType() == Double.class)
            return Opcodes.DRETURN;

        return Opcodes.ARETURN;
    }

    public int getLoadOpcodes() {
        if (this.getType() == Integer.class || this.getType() == Byte.class || this.getType() == Short.class || this.getType() == Character.class || this.getType() == Boolean.class)
            return Opcodes.ILOAD;
        if (this.getType() == Long.class)
            return Opcodes.LLOAD;
        if (this.getType() == Float.class)
            return Opcodes.FLOAD;

        if (this.getType() == Integer[].class)
            return Opcodes.IALOAD;
        if (this.getType() == Long[].class)
            return Opcodes.LALOAD;
        if (this.getType() == Float[].class)
            return Opcodes.FALOAD;
        if (this.getType() == Double[].class)
            return Opcodes.DALOAD;
        if (this.getType() == Byte[].class || this.getType() == boolean[].class || this.getType() == Boolean[].class)
            return Opcodes.BALOAD;
        if (this.getType() == Short[].class)
            return Opcodes.SALOAD;
        if (this.getType() == Character[].class)
            return Opcodes.CALOAD;
        return Opcodes.ALOAD;
    }
}