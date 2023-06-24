package me.xiaoying.sb.task.tasks;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.AutoReSpawnConstant;
import me.xiaoying.sb.task.TaskHandle;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 线程任务 AutoReSpawn
 */
public class AutoReSpawnTask implements TaskHandle {
    int task;

    @Override
    public void run() {
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
            for (Player onlinePlayer : ServerUtil.getOnlinePlayers()) {
                if (!onlinePlayer.isDead())
                    continue;

                onlinePlayer.spigot().respawn();
            }
        }, 0L, AutoReSpawnConstant.CHECK_TIME);
    }

    @Override
    public void stop() {
        try {
            Bukkit.getScheduler().cancelTask(this.task);
        } catch (Exception e) { /**/ }
    }

    @Override
    public Integer getTask() {
        return this.task;
    }
}