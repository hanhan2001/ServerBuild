package me.xiaoying.serverbuild.entity;

import org.bukkit.entity.Player;

import java.util.List;

public class AutoRespawnEntity {
    private final String permission;
    private final List<String> scripts;
    private final int priority;

    public AutoRespawnEntity(String permission, int priority, List<String> scripts) {
        this.permission = permission;
        this.priority = priority;
        this.scripts = scripts;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<String> getScripts() {
        return this.scripts;
    }

    public boolean useful(Player player) {
        return player.hasPermission(this.getPermission()) || player.isOp();
    }
}