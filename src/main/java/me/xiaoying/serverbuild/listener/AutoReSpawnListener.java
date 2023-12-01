package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.constant.ConstantAutoReSpawn;
import me.xiaoying.serverbuild.factory.VariableFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.concurrent.TimeUnit;

/**
 * Listener AutoReSpawn
 */
public class AutoReSpawnListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        ServerBuild.getTaskService().getScheduledExecutor().schedule(() -> {
            event.getEntity().spigot().respawn();

            ConstantAutoReSpawn constantAutoReSpawn = (ConstantAutoReSpawn) ServerBuild.getFunctionService().getFunction("AutoReSpawn").getFiles().get(0).getConstant();
            for (String s : constantAutoReSpawn.AUTORESPAWN_EVENT) {
                s = new VariableFactory(s)
                        .date(constantAutoReSpawn.SET_DATEFORMAT)
                        .player(event.getEntity().getName())
                        .color()
                        .toString();
                ServerBuild.getScriptManager().onCommand(s);
            }
        }, ((ConstantAutoReSpawn) ServerBuild.getFunctionService().getFunction("AutoReSpawn").getFiles().get(0).getConstant()).AUTORESPAWN_TICK, TimeUnit.MILLISECONDS);
    }
}