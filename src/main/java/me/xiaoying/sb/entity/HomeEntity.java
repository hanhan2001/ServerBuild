package me.xiaoying.sb.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * 实体类 Home
 */
public class HomeEntity {
    String name, world;
    double x, y, z;
    float yaw, pitch;

    public HomeEntity(String string) {
        this.name = string.split("\\|")[0];

        String[] strings = string.split("\\|")[1].split(":");
        this.world = strings[0];
        this.x = Double.parseDouble(strings[1]);
        this.y = Double.parseDouble(strings[2]);
        this.z = Double.parseDouble(strings[3]);
        this.yaw = Float.parseFloat(strings[4]);
        this.pitch = Float.parseFloat(strings[5]);
    }

    public HomeEntity(String name, Player player) {
        Location location = player.getLocation();

        this.name = name;
        this.world = Objects.requireNonNull(location.getWorld()).getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public String getName() {
        return this.name;
    }

    public String getWorld() {
        return this.world;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Location getLocation() {
        return new Location(Bukkit.getServer().getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public String toString() {
        return this.name + "|" + this.world + ":" + this.x + ":" + this.y + ":" + this.z + ":" + this.yaw + ":" + this.pitch;
    }
}