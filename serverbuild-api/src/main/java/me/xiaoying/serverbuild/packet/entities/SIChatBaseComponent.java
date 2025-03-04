package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.network.chat.IChatBaseComponent")
public abstract class SIChatBaseComponent implements SProxy {
    @SMethod(methodName = "a", needInstance = false)
    public abstract Object string(@SParameter(index = 0) String string);
}