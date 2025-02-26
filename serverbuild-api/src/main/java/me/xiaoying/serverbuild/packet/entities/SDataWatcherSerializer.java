package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

@SClass(type = SClass.Type.OTHER, className = "net.minecraft.network.syncher.DataWatcherSerializer")
public abstract class SDataWatcherSerializer implements SProxy {
    @SMethod(methodName = "a")
    public abstract void serializeV1_20_R1(@SParameter(index = 0) Object value);

    @SMethod(methodName = "a", returnClass = "net.minecraft.network.syncher.DataWatcher")
    public abstract Object serializeV1_20_R1(@SParameter(index = 0) int id);
}