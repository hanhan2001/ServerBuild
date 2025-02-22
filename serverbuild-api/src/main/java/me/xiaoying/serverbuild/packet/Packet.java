package me.xiaoying.serverbuild.packet;

import org.bukkit.entity.Player;

public interface Packet {
    void sendHologram(Player player, String text);
}