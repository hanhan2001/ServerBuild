package me.xiaoying.serverbuild.packet;

import org.bukkit.Bukkit;

public class PacketManager {
    private Packet packet;
    private final String latestVersion = "v1.20.1";

    public PacketManager() {
        String version = Bukkit.getServer().getBukkitVersion();

        String[] splitByPoint = version.split("\\.");
        String[] splitByBar = version.split("-");

        String prefix = splitByPoint[0] + "_" + splitByPoint[1];
        String suffix = splitByBar[1];

        if (suffix.contains("."))
            suffix = String.valueOf(suffix.charAt(0)) + suffix.charAt(3);

        version = "v" + prefix + "_" + suffix;

        String className = "me.xiaoying.serverbuild.packet." + version;
        try {
            this.packet = (Packet) Class.forName(className).getConstructors()[0].newInstance();
        } catch (Exception e) {
            try {
                this.packet = (Packet) Class.forName("me.xiaoying.serverbuild.packet." + this.latestVersion).getConstructors()[0].newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}