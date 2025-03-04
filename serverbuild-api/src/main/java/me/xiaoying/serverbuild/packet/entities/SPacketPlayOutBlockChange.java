package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SConstructor;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.network.protocol.game.PacketPlayOutBlockChange")
public abstract class SPacketPlayOutBlockChange implements SProxy {
    @SConstructor(target = "net.minecraft.network.protocol.game.PacketPlayOutBlockChange")
    public abstract Object constructorV1_20_R1(@SParameter(index = 0, truthClass = "net.minecraft.core.BlockPosition") Object blockPosition, @SParameter(index = 1, truthClass = "net.minecraft.world.level.block.state.IBlockData") Object state);
}