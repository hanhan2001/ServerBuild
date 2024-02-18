package me.xiaoying.serverbuild.task.tasks;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.constant.ConstantAutoReSpawn;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.task.SubTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * TaskService AutoReSpawn
 */
public class AutoReSpawnTask implements SubTask {
    int task;
    boolean isRunning = false;

    @Override
    public int getId() {
        return this.task;
    }

    @Override
    public String getName() {
        return "AutoReSpawn";
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    @Override
    public void run() {
        this.setRunning(true);

        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (!onlinePlayer.isDead())
                    continue;

                onlinePlayer.spigot().respawn();

                ConstantAutoReSpawn constantAutoReSpawn = (ConstantAutoReSpawn) ServerBuild.getFunctionService().getFunction("AutoReSpawn").getFiles().get(0).getConstant();
                for (String s : constantAutoReSpawn.AUTORESPAWN_EVENT) {
                    s = new VariableFactory(s)
                            .prefix(constantAutoReSpawn.SET_PREFIX)
                            .date(constantAutoReSpawn.SET_DATEFORMAT)
                            .player(onlinePlayer.getName())
                            .color()
                            .toString();
                    ServerBuild.getScriptManager().onCommand(s);
                }
            }
        }, 0L, ((ConstantAutoReSpawn) ServerBuild.getFunctionService().getFunction("AutoReSpawn").getFiles().get(0).getConstant()).AUTORESPAWN_TICK);
    }

    @Override
    public void stop() {
        this.setRunning(false);

        try { Bukkit.getScheduler().cancelTask(this.task); } catch (Exception e) {}
    }
}