package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SConstructor;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.core.BlockPosition")
public abstract class SBlockPosition implements SProxy {
    @SConstructor(target = "net.minecraft.core.BlockPosition")
    public abstract Object constructorV1_20_R1(@SParameter(index = 0) int x, @SParameter(index = 1) int y, @SParameter(index = 2) int z);
}