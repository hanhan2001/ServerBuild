package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SConstructor;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.network.protocol.game.PacketPlayOutEntityStatus")
public abstract class SPacketPlayOutEntityStatus implements SProxy {
    @SConstructor(target = "net.minecraft.network.protocol.game.PacketPlayOutEntityStatus")
    public abstract Object getPacketV1_20_R1(@SParameter(index = 0, truthClass = "net.minecraft.world.entity.Entity") Object entity, @SParameter(index = 1) byte status);
}