package me.xiaoying.sb.task.tasks;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.ClearEntityConstant;
import me.xiaoying.sb.event.ClearEntityEvent;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.ClearEntityService;
import me.xiaoying.sb.task.TaskHandle;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class  ClearEntityTask implements TaskHandle {
    int task;

    @Override
    public void run() {
        final int[] time = {ClearEntityConstant.CLEAR_SECOND};
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
            if (time[0] != 0 && ClearEntityConstant.CLEAR_TIME.contains(String.valueOf(time[0]))) {
                ServerUtil.getOnlinePlayers().forEach(player -> {
                    for (String s : ClearEntityConstant.CLEAR_MESSAGE_COUNTDOWN)
                        player.sendMessage(new VariableFactory(s)
                                .prefix(ClearEntityConstant.MESSAGE_PREFIX)
                                .date(ClearEntityConstant.SET_VARIABLE_DATEFORMAT)
                                .time(String.valueOf(time[0]))
                                .player(player)
                                .placeholder(player)
                                .color()
                                .getString());
                });
            }

            if (--time[0] > 0)
                return;

            List<Entity> list = new ArrayList<>();

            for (World world : Bukkit.getServer().getWorlds())
                list = ClearEntityService.getWorldEntity(world);
            if (ClearEntityConstant.CLEAR_TOTAL && list.size() < ClearEntityConstant.CLEAR_TOTAL_LIMIT) {
                time[0] = ClearEntityConstant.CLEAR_SECOND;
                return;
            }

            list.forEach(Entity::remove);
            Bukkit.getServer().getPluginManager().callEvent(new ClearEntityEvent(list));
            time[0] = ClearEntityConstant.CLEAR_SECOND;
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
        return new Integer[]{this.task};
    }
}