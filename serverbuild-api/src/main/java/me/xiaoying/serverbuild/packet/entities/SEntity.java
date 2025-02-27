package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.*;

@SClass(type = SClass.Type.OTHER, className = "net.minecraft.world.entity.Entity")
public abstract class SEntity implements SProxy {
    @SFieldMethod(fieldName = "aU", type = SFieldMethod.Type.GETTER)
    public abstract Object get_CUSTOM_NAME_V1_20_R1();

    @SFieldMethod(fieldName = "aV", type = SFieldMethod.Type.GETTER)
    public abstract Object get_CUSTOM_NAME_VISIBLE_V1_20_R1();

    @SFieldMethod(fieldName = "am", type = SFieldMethod.Type.GETTER)
    public abstract Object getDataWatcherV1_20_R0();

    @SMethod(methodName = "f")
    public abstract void loadV1_20_R1(@SParameter(index = 0, truthClass = "net.minecraft.nbt.NBTTagCompound") Object nbt);

    @SMethod(methodName = "al", returnClass = "net.minecraft.world.entity.EntityPose")
    public abstract Object getPoseV1_20_R1();
}