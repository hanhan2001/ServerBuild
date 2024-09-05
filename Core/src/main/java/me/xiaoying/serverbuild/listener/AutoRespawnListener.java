package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AutoRespawnListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!FileAutoRespawn.AUTO_RESPAWN_TYPE.equalsIgnoreCase("Player"))
            return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(SBPlugin.getInstance(), () -> AutoRespawnListener.this.respawn(event.getEntity()), FileAutoRespawn.AUTO_RESPAWN_PLAYER);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().isDead())
            return;

        this.respawn(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!event.getPlayer().isDead())
            return;

        this.respawn(event.getPlayer());
    }

    private void respawn(Player player) {
        player.spigot().respawn();
        for (String s : FileAutoRespawn.AUTO_RESPAWN_SCRIPT.split("\n"))
            SBPlugin.getScriptManager().performScript(new VariableFactory(s).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).player(player).placeholder(player).color().toString(), player);
    }
}