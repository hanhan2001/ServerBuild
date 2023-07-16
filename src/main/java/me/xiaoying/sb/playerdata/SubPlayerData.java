package me.xiaoying.sb.playerdata;

import org.bukkit.entity.Player;

public abstract class SubPlayerData {
    public abstract Object getPlayerData(String player);

    public abstract Object getPlayerData(Player player);
}