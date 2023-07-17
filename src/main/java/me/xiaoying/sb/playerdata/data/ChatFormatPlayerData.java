package me.xiaoying.sb.playerdata.data;

import me.xiaoying.mf.SqlType;
import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.playerdata.SubPlayerData;
import org.bukkit.entity.Player;

public class ChatFormatPlayerData extends SubPlayerData {
    @Override
    public Object getPlayerData(String player) {
        ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.CREATE)
                .table("chatformat_mute")
                .create("player", "varchar", 255)
                .create("save", "varchar", 255)
                .create("mute", "varchar", 255);
        String time = (String) ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.SELECT)
                .table("chatformat_mute")
                .condition("player", player)
                .run().get("mute").get(0);

        if (time == null)
            return 0;

        return Integer.parseInt(time);
    }

    @Override
    public Object getPlayerData(Player player) {
        return getPlayerData(player.getName());
    }
}