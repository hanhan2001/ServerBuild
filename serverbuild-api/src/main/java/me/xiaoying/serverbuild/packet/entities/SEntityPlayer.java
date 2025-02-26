package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SFieldMethod;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.server.level.EntityPlayer")
public abstract class SEntityPlayer implements SProxy {
    @SFieldMethod(filedName = "c", type = SFieldMethod.Type.GETTER)
    public abstract Object getPlayConnectionV1_20_R1();
}