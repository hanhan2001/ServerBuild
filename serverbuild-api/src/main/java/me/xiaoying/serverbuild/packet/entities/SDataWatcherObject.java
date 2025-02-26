package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SConstructor;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.OTHER, className = "net.minecraft.network.syncher.DataWatcherObject")
public abstract class SDataWatcherObject implements SProxy {
    @SConstructor(target = "net.minecraft.network.syncher.DataWatcherObject")
    public abstract Object getDataWatcherObjectV1_20_R1(@SParameter(index = 0) int id, @SParameter(index = 1, truthClass = "net.minecraft.network.syncher.DataWatcherSerializer") Object dataWatcherSerializer);
}