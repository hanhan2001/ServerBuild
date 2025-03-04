package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SFieldMethod;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.world.entity.player.EntityHuman")
public abstract class SEntityHuman implements SProxy {
    @SFieldMethod(fieldName = "bM", type = SFieldMethod.Type.GETTER)
    public abstract Object getPoseDataWatcherV1_20_R1();
}