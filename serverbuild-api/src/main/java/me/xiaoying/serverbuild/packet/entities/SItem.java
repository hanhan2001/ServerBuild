package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.world.item.Item")
public abstract class SItem implements SProxy {
    @SMethod(methodName = "b", returnClass = "net.minecraft.world.item.Item", needInstance = false)
    public abstract Object getItem(@SParameter(index = 0) int id);
}