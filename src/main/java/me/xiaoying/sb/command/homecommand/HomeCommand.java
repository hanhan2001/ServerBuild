package me.xiaoying.sb.command.homecommand;

import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.metadata.CustomHomeMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
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
        if (player.getMetadata("home").size() == 0) {
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

        if (player.getMetadata("home").size() == 0) {
            player.sendMessage(new VariableFactory(HomeConstant.MESSAGE_HOME_NOT_EXISTS)
                            .prefix(HomeConstant.MESSAGE_PREFIX)
                            .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                            .player(player)
                            .placeholder(player)
                            .color()
                            .getString());
            return false;
        }

        for (MetadataValue metadatum : player.getMetadata("home")) {
            if (!metadatum.value().toString().split("\\|")[0].equalsIgnoreCase(home))
                continue;

            String locationString = metadatum.value().toString().split("\\|")[1];
            String[] split = locationString.split(":");
            World world = Bukkit.getServer().getWorld(split[0]);
            double x = Double.parseDouble(split[1]);
            double y = Double.parseDouble(split[2]);
            double z = Double.parseDouble(split[3]);
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);
            player.teleport(new Location(world, x, y, z, yaw, pitch));

//            player.teleport(((CustomHomeMetaData) metadatum).getLocation());
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

        Player player = (Player) sender;
        List<String> l = new ArrayList<>();
        for (MetadataValue home : player.getMetadata("home"))
            l.add(((CustomHomeMetaData) home).getName());
        return l;
    }
}