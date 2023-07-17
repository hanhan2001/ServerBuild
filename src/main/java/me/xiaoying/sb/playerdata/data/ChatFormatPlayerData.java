package me.xiaoying.sb.playerdata.data;

import me.xiaoying.mf.SqlType;
import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.playerdata.SubPlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFormatPlayerData extends SubPlayerData {
    /**
     * 获取玩家禁言信息
     *
     * @param player 玩家
     * @return Map
     */
    @Override
    public Object getPlayerData(String player) {
        ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.CREATE)
                .table("chatformat_mute")
                .create("player", "varchar", 255)
                .create("save", "varchar", 255)
                .create("mute", "varchar", 255)
                .run();
        Map<String, List<Object>> source =  ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.SELECT)
                .table("chatformat_mute")
                .condition("player", player)
                .run();

        // 开始禁言时间
        String saveTime;
        if (source.get("save").size() != 0)
            saveTime = (String) source.get("save").get(0);
        else
            return null;

        // 禁言毫秒
        int time = 0;
        List<Object> list = source.get("mute");
        if (list.size() != 0)
            return Integer.parseInt((String) list.get(0));

        // 存入Map数据
        Map<String, Object> map = new HashMap<>();
        map.put("save", saveTime);
        map.put("mute", time);

        return map;
    }

    @Override
    public Object getPlayerData(Player player) {
        return getPlayerData(player.getName());
    }

    @Override
    public void setPlayerData(String player, Object object) {

    }

    @Override
    public void setPlayerData(Player player, Object object) {

    }
}