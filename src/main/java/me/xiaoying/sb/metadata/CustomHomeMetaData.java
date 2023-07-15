package me.xiaoying.sb.metadata;

import me.xiaoying.sb.ServerBuild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * metadata Home
 */
public class CustomHomeMetaData implements MetadataValue {
    String home;

    public CustomHomeMetaData(String name, Player player) {
        Location location = player.getLocation();
        String world = Objects.requireNonNull(location.getWorld()).getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        this.home = name + "|" + world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
    }

    public String getName() {
        return this.home.split("\\|")[0];
    }

    public Location getLocation() {
        String locationString = this.home.split("\\|")[1];
        String[] split = locationString.split(":");
        World world = Bukkit.getServer().getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Nullable
    @Override
    public Object value() {
        return this.home;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public long asLong() {
        return 0;
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
        return this.getName();
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