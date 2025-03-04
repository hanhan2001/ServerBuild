package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SFieldMethod;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.network.syncher.DataWatcherRegistry")
public abstract class SDataWatcherRegistry implements SProxy {
    @SFieldMethod(fieldName = "v", type = SFieldMethod.Type.GETTER, needInstance = false)
    public abstract Object getDataWatcherSerializerOfEntityPoseV1_20_R1();
}