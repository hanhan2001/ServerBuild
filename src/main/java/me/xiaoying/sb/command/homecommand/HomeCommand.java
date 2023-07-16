package me.xiaoying.sb.command.homecommand;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.entity.HomeEntity;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.metadata.CustomHomeMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        List<HomeEntity> list = (List<HomeEntity>) ServerBuild.getPlayerDataService().getData("Home").getPlayerData(player);
        if (list.size() == 0) {
            player.sendMessage(new VariableFactory(HomeConstant.MESSAGE_HOME_NOT_EXISTS)
                            .prefix(HomeConstant.MESSAGE_PREFIX)
                            .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                            .player(player)
                            .placeholder(player)
                            .color()
                            .getString());
            return false;
        }

        String home;
        if (strings.length == 0)
            home = "home";
        else
            home = strings[0];

        // 检测传送家
        for (HomeEntity homeEntity : list) {
            if (!homeEntity.getName().equalsIgnoreCase(home))
                continue;

            player.teleport(homeEntity.getLocation());
            return false;
        }

        player.sendMessage(new VariableFactory(HomeConstant.MESSAGE_HOME_NOT_EXISTS)
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
        if (!(sender instanceof Player))
            return new ArrayList<>();


        if (strings.length > 1)
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