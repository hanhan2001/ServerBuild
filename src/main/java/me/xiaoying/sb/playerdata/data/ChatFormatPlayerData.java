package me.xiaoying.sb.playerdata.data;

import me.xiaoying.mf.SqlType;
import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.playerdata.SubPlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFormatPlayerData extends SubPlayerData {
    String table = "chatformat_mute";

    @Override
    public String getName() {
        return "ChatFormat_Mute";
    }

    @Override
    public void fileData() {
        ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.CREATE)
                .table(this.table)
                .create("player", "varchar", 255)
                .create("save", "varchar", 255)
                .create("mute", "varchar", 255)
                .run();
    }

    /**
     * 获取玩家禁言信息
     *
     * @param player 玩家
     * @return Map
     */
    @Override
    public Object getPlayerData(String player) {
        Map<String, List<Object>> source =  ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.SELECT)
                .table(this.table)
                .cols("player", "save", "mute")
                .condition("player", player)
                .run();

        // 开始禁言时间
        String saveTime;
        if (source.get("save") == null)
            return null;
        else if (source.get("save").size() != 0)
            saveTime = (String) source.get("save").get(0);
        else
            return null;

        // 存入Map数据
        Map<String, Object> map = new HashMap<>();
        map.put("save", saveTime);
        map.put("mute", Float.parseFloat(source.get("mute").get(0).toString()));

        return map;
    }

    @Override
    public Object getPlayerData(Player player) {
        return getPlayerData(player.getName());
    }

    @Override
    public void setPlayerData(String player, Object[] object) {
        float time = Float.parseFloat(object[1].toString());

        if (time == 0) {
            this.delPlayerData(player);
            return;
        }

        if (ServerBuild.getPlayerDataService().getSqlFactory()
                .type(SqlType.SELECT)
                .table(this.table)
                .cols("player", "mute")
                .condition("player", player)
                .run().get("mute") == null) {
            ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.INSERT)
                    .table(this.table)
                    .insert(player, (String) object[0], String.valueOf(time)).run();
            return;
        }

        ServerBuild.getPlayerDataService().getSqlFactory()
                .type(SqlType.UPDATE)
                .table(this.table)
                .set("mute", String.valueOf(time))
                .set("save", (String) object[0])
                .condition("player", player)
                .run();
    }

    @Override
    public void setPlayerData(Player player, Object[] object) {
        this.setPlayerData(player.getName(), object);
    }

    @Override
    public void delPlayerData(String player) {
        ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.DELETE)
                .table(this.table)
                .condition("player", player).run();
    }

    @Override
    public void delPlayerData(Player player) {
        this.delPlayerData(player.getName());
    }
}