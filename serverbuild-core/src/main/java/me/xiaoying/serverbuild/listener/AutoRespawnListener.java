package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.AutoRespawnEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.module.AutoRespawnModule;
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
        AutoRespawnEntity autoRespawnEntity = null;

        AutoRespawnModule module = (AutoRespawnModule) SBPlugin.getModuleManager().getModule("AutoRespawn");
        for (AutoRespawnEntity entity : module.getAutoRespawnEntities()) {
            if (!entity.useful(player))
                continue;

            if (autoRespawnEntity == null) {
                autoRespawnEntity = entity;
                continue;
            }

            if (autoRespawnEntity.getPriority() < entity.getPriority())
                continue;

            autoRespawnEntity = entity;
        }

        player.spigot().respawn();

        if (autoRespawnEntity == null)
            return;

        for (String script : autoRespawnEntity.getScripts())
            SBPlugin.getScriptManager().performScript(new VariableFactory(script).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).player(player).placeholder(player).color().toString(), player);
    }
}