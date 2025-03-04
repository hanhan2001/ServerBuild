package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;

@SClass(type = SClass.Type.CRAFT_BUKKIT, className = "entity.CraftPlayer")
public abstract class SCraftPlayer implements SProxy {
    @SMethod(methodName = "getHandle", returnClass = "net.minecraft.server.level.EntityPlayer")
    public abstract Object getHandleV1_20_R1();
}