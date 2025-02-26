package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SConstructor;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

import java.util.List;

@SClass(type = SClass.Type.OTHER, className = "")
public abstract class SPacketPlayOutEntityMetadata implements SProxy {
    @SConstructor(target = "net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata")
    public abstract Object getPacketV1_20_R1(@SParameter(index = 0) int entityId, @SParameter(index = 1) List<Object> list);
}