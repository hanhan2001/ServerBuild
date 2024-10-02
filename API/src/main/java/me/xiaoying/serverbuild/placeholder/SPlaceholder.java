package me.xiaoying.serverbuild.placeholder;

import org.bukkit.entity.Player;

public interface SPlaceholder {
    String getKey();

    String replace(Player player);
}