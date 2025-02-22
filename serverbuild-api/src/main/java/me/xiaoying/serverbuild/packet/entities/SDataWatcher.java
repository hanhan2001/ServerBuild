package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SConstructor;
import org.bukkit.entity.Entity;

@SClass(type = SClass.Type.OTHER, className = "")
public abstract class SDataWatcher implements SProxy {
    @SConstructor(target = "net.minecraft.server.v1_8_R3.DataWatcher")
    public abstract Object getV_1_8_R3(Entity entity);
}