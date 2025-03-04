package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.world.entity.EntityPose")
public abstract class SEntityPose implements SProxy {
    @SMethod(methodName = "valueOf", returnClass = "net.minecraft.world.entity.EntityPose", needInstance = false)
    public abstract Object getPoseV1_20_R1(@SParameter(index = 0) String pose);
}