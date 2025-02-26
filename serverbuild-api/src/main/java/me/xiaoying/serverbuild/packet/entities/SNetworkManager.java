package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.network.NetworkManager")
public abstract class SNetworkManager implements SProxy {
    @SMethod(methodName = "a")
    public abstract void sendPacketV1_20_R1(@SParameter(index = 0, truthClass = "net.minecraft.network.protocol.Packet") Object packet);
}