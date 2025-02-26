package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SConstructor;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;

@SClass(type = SClass.Type.OTHER, className = "net.minecraft.nbt.NBTTagCompound")
public abstract class SNBTTagCompound implements SProxy {
    @SConstructor(target = "net.minecraft.nbt.NBTTagCompound")
    public abstract Object getNBTTagCompoundV1_20_R1();

    @SMethod(methodName = "a")
    public abstract void setString(String key, String value);

    public abstract Object getInstance();
}