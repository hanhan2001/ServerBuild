package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.*;

@SClass(type = SClass.Type.OTHER, className = "net.minecraft.world.entity.Entity")
public abstract class SEntity implements SProxy {
    @SFieldMethod(filedName = "am", type = SFieldMethod.Type.GETTER)
    public abstract Object getDataWatcherV1_20_R0();

    @SMethod(methodName = "f")
    public abstract void loadV1_20_R1(@SParameter(index = 0, truthClass = "net.minecraft.nbt.NBTTagCompound") Object nbt);

    @SField(fieldName = "aU", version = "v1_20_R1")
    public Object CUSTOM_NAME_V1_20_R1;

    @SField(fieldName = "aV", version = "v1_20_R1")
    public Object CUSTOM_NAME_VISIBLE_V1_20_R1;
}