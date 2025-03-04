package me.xiaoying.serverbuild.packet.entities;

import me.xiaoying.serverbuild.proxy.SProxy;
import me.xiaoying.serverbuild.proxy.annotation.SClass;
import me.xiaoying.serverbuild.proxy.annotation.SFieldMethod;

@SClass(type = SClass.Type.NULL, className = "net.minecraft.world.level.block.Blocks")
public abstract class SBlocks implements SProxy {
    @SFieldMethod(fieldName = "hW", type = SFieldMethod.Type.GETTER, needInstance = false)
    public abstract Object BARRIERV1_20_R1();
}