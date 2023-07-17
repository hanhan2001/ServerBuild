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
    public String getName() {
        return "Home";
    }

    @Override
    public void fileData() {
        ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.CREATE)
                .table(this.table)
                .create("player", "varchar", 255)
                .create("homes", "longtext", 0)
                .run();
    }

    @Override
    public Object getPlayerData(String player) {
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

    @Override
    public void setPlayerData(String player, Object[] object) {

    }

    @Override
    public void setPlayerData(Player player, Object[] object) {

    }

    @Override
    public void delPlayerData(String player) {

    }

    @Override
    public void delPlayerData(Player player) {

    }
}