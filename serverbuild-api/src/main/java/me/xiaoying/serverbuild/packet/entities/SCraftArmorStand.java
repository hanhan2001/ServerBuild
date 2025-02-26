package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;

@SClass(type = SClass.Type.OTHER, className = "org.bukkit.craftbukkit.v1_20_R1.entity.CraftArmorStand")
public abstract class SCraftArmorStand implements SProxy {
    @SMethod(methodName = "getHandle", returnClass = "net.minecraft.world.entity.decoration.EntityArmorStand")
    public abstract Object getHandleV1_20_R1();
}