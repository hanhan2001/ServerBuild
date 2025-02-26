package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SFieldMethod;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.server.network.PlayerConnection")
public abstract class SPlayerConnection implements SProxy {
    @SFieldMethod(filedName = "h", type = SFieldMethod.Type.GETTER)
    public abstract Object getNetworkManagerV1_20_R1();
}