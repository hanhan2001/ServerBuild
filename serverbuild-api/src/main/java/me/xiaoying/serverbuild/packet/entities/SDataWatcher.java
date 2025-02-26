package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SMethod;
import me.xiaoying.serverbuild.proxy.annotation.SParameter;

import java.util.List;

@SClass(type = SClass.Type.OTHER, className = "net.minecraft.network.syncher.DataWatcher")
public abstract class SDataWatcher implements SProxy {
    @SMethod(methodName = "b", returnClass = "java/util/List")
    public abstract List<Object> getItemListV1_20_R0();

    @SMethod(methodName = "b")
    public abstract void setV1_20_R1(@SParameter(index = 0, truthClass = "net.minecraft.network.syncher.DataWatcherObject") Object dataWatcherObject, @SParameter(index = 1) Object value);
}