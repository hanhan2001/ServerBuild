package me.xiaoying.serverbuild.utils;

import org.bukkit.Location;

public class LocationUtil {
    /**
     * 判断两个 Location 是否相等<br>
     * 若使用 {@code location1 == location2} 可能会因为内存地址不同导致判断值错误
     *
     * @param location1 坐标1
     * @param location2 坐标2
     * @return 是否相等
     */
    public static boolean equal(Location location1, Location location2) {
        String format = "world:x:y:z";
        return LocationUtil.locationToString(location1, format).equalsIgnoreCase(LocationUtil.locationToString(location2, format));
    }

    /**
     * 字符串转Location
     * 只可做绝对位置
     *
     * @param string 原字符串
     * @param format 字符串使用
     * @return Location
     */
    public static Location stringToLocation(String string, String format) {
        return null;
    }

    /**
     * Location转字符串
     *
     * @param location Location
     * @param format 格式
     * @return Location
     */
    public static String locationToString(Location location, String format) {
        format = format.replace("yaw", String.valueOf(location.getYaw()));
        format = format.replace("pitch", String.valueOf(location.getPitch()));
        format = format.replace("world", location.getWorld().getName());
        format = format.replace("x", String.valueOf(location.getX()));
        format = format.replace("y", String.valueOf(location.getY()));
        format = format.replace("z", String.valueOf(location.getZ()));
        return format;
    }
}