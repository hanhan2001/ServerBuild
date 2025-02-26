package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.packet.spigot.V1_20_R1;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PacketListener implements Listener {
    @EventHandler
    public void onPlayerChatEvent(PlayerJoinEvent event) {
        new V1_20_R1().sendHologram(event.getPlayer(), "TTTTTTT");
    }
}