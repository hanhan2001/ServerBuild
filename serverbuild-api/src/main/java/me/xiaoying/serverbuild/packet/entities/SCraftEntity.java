package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import org.bukkit.entity.Pose;

@SClass(type = SClass.Type.CRAFT_BUKKIT, className = "entity.CraftEntity")
public abstract class SCraftEntity implements SProxy {
    @SMethod(methodName = "getPose")
    public abstract Pose getPose();
}