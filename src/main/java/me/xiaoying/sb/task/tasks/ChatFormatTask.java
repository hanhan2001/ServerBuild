package me.xiaoying.sb.task.tasks;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.metadata.MuteMetaData;
import me.xiaoying.sb.task.TaskHandle;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class ChatFormatTask implements TaskHandle {
    int task;

    @Override
    public void run() {
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
            for (Player player : ServerUtil.getOnlinePlayers()) {
                if (player.getMetadata("mute").size() == 0)
                    continue;

                float time = player.getMetadata("mute").get(0).asFloat();
                player.removeMetadata("mute", ServerBuild.getInstance());
                if (time - 1 > 0)
                    player.setMetadata("mute", new MuteMetaData(time - 1, TimeUnit.SECONDS));
            }
        }, 0L, 20L);
    }

    @Override
    public void stop() {
        try {
            Bukkit.getServer().getScheduler().cancelTask(this.task);
        } catch (Exception e) { /**/ }
    }

    @Override
    public Integer[] getTasks() {
        return new Integer[] {this.task};
    }
}
