package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SFieldMethod;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.world.level.block.Block")
public abstract class SBlock implements SProxy {
    @SMethod(methodName = "n", returnClass = "net.minecraft.world.level.block.state.IBlockData")
    public abstract Object getBlockData();

    @SMethod(methodName = "a", returnClass = "net.minecraft.world.level.block.Block", needInstance = false)
    public abstract Object getBlock(@SParameter(index = 0, truthClass = "net.minecraft.world.item.Item") Object item);

    @SFieldMethod(fieldName = "e", type = SFieldMethod.Type.GETTER)
    public abstract String e();
}