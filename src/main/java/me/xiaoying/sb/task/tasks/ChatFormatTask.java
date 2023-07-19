package me.xiaoying.sb.task.tasks;

import me.xiaoying.mf.SqlType;
import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.playerdata.SubPlayerData;
import me.xiaoying.sb.playerdata.data.ChatFormatPlayerData;
import me.xiaoying.sb.task.TaskHandle;
import me.xiaoying.sb.utils.DateUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChatFormatTask implements TaskHandle {
    String table = "chatformat_mute";
    int task;

    @Override
    public void run() {
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
            for (Player player : ServerUtil.getOnlinePlayers()) {
                SubPlayerData playerData = ServerBuild.getPlayerDataService().getData("chatformat", "chatformat_mute");

                if (playerData.getPlayerData(player) == null)
                    continue;

                Map<String, Object> map = (Map<String, Object>) playerData.getPlayerData(player);
                String save = (String) map.get("save");
                float time = Float.parseFloat(map.get("mute").toString());
                float reduce = DateUtil.getDateReduce(DateUtil.getDate(ChatFormatConstant.SET_VARIABLE_DATEFORMAT), save, ChatFormatConstant.SET_VARIABLE_DATEFORMAT);
                if (reduce / 1000 < time)
                    continue;

                ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.DELETE)
                        .table(this.table)
                        .condition("player", player.getName())
                        .run();
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
