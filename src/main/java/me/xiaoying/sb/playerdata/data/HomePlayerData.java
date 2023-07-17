package me.xiaoying.sb.playerdata.data;

import me.xiaoying.mf.SqlType;
import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.entity.HomeEntity;
import me.xiaoying.sb.playerdata.SubPlayerData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerData Home
 */
public class HomePlayerData extends SubPlayerData {
    String table = "home";

    @Override
    public Object getPlayerData(String player) {
        ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.CREATE)
                .table(this.table)
                .create("player", "varchar", 255)
                .create("homes", "longtext", 0)
                .run();

        List<Object> list = ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.SELECT)
                .table(this.table)
                .cols("player", "homes")
                .condition("player", player)
                .run()
                .get("homes");

        if (list == null)
            return new ArrayList<>();

        String source = (String) list.get(0);
        List<HomeEntity> homes = new ArrayList<>();
        for (String s : source.split(","))
            homes.add(new HomeEntity(s));
        return homes;
    }

    @Override
    public Object getPlayerData(Player player) {
        return this.getPlayerData(player.getName());
    }
}