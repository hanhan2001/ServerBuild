package me.xiaoying.sb.metadata;

import me.xiaoying.sb.ServerBuild;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public class MuteMetaData implements MetadataValue {
    float time;

    public MuteMetaData(float time, TimeUnit timeUnit) {
        if (timeUnit == TimeUnit.MINUTES)
            this.time = time * 60;
        else if (timeUnit == TimeUnit.SECONDS)
            this.time = time;
        else if (timeUnit == TimeUnit.DAYS)
            this.time = time * 60 * 60 * 24;
    }

    @Nullable
    @Override
    public Object value() {
        return this.time;
    }

    @Override
    public int asInt() {
        return (int) this.time;
    }

    @Override
    public float asFloat() {
        return this.time;
    }

    @Override
    public double asDouble() {
        return this.time;
    }

    @Override
    public long asLong() {
        return (long) this.time;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @NotNull
    @Override
    public String asString() {
        return String.valueOf(this.time);
    }

    @Nullable
    @Override
    public Plugin getOwningPlugin() {
        return ServerBuild.getInstance();
    }

    @Override
    public void invalidate() {

    }
}