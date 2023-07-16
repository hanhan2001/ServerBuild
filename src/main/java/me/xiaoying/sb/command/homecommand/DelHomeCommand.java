package me.xiaoying.sb.command.homecommand;

import me.xiaoying.mf.SqlType;
import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.entity.HomeEntity;
import me.xiaoying.sb.factory.VariableFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DelHomeCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(new VariableFactory(HomeConstant.MESSAGE_ERROR_IDENTITY)
                    .prefix(HomeConstant.MESSAGE_PREFIX)
                    .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                    .color()
                    .getString());
            return false;
        }

        Player player = (Player) sender;
        List<HomeEntity> list = (List<HomeEntity>) ServerBuild.getPlayerDataService().getData("Home").getPlayerData(player);
        String home;

        if (strings.length > 0)
            home = strings[0];
        else
            home = "home";

        HomeEntity homeEntity = null;
        for (HomeEntity home1 : list) {
            if (!home1.getName().equalsIgnoreCase(home))
                continue;

            homeEntity = home1;
        }

        if (homeEntity == null) {
            sender.sendMessage(new VariableFactory(HomeConstant.MESSAGE_HOME_NOT_EXISTS)
                    .prefix(HomeConstant.MESSAGE_PREFIX)
                    .player(player)
                    .placeholder(player)
                    .color()
                    .getString());
            return false;
        }

        list.remove(homeEntity);

        // 删除后不存在家时
        if (list.size() == 0) {
            ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.DELETE)
                    .table("home")
                    .condition("player", player.getName())
                    .run();

            player.sendMessage(new VariableFactory(HomeConstant.MESSAGE_SET_SUCCESS)
                            .prefix(HomeConstant.MESSAGE_PREFIX)
                            .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                            .player(player)
                            .placeholder(player)
                            .color()
                            .getString());
            return false;
        }

        // 删除后存在多个家时
        StringBuilder stringBuilder = new StringBuilder();
        for (HomeEntity entity : list) {
            if (stringBuilder.toString().isEmpty()) {
                stringBuilder.append(entity.toString());
                continue;
            }

            stringBuilder.append(",").append(entity.toString());
        }

        ServerBuild.getPlayerDataService().getSqlFactory().type(SqlType.UPDATE)
                .table("home")
                .set("homes", stringBuilder.toString())
                .condition("player", player.getName())
                .run();

        player.sendMessage(new VariableFactory(HomeConstant.MESSAGE_SET_SUCCESS)
                .prefix(HomeConstant.MESSAGE_PREFIX)
                .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                .player(player)
                .placeholder(player)
                .color()
                .getString());
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 1 || !(sender instanceof Player))
            return new ArrayList<>();

        Player player = (Player) sender;
        List<String> list = new ArrayList<>();
        List<HomeEntity> homes = (List<HomeEntity>) ServerBuild.getPlayerDataService().getData("Home").getPlayerData(player);
        for (HomeEntity home : homes)
            list.add(home.getName());

        List<String> conditionList = new ArrayList<>();
        for (String s1 : list) {
            if (!s1.startsWith(strings[0]))
                continue;
            conditionList.add(s1);
        }

        if (conditionList.size() == 0)
            return list;
        return conditionList;
    }
}