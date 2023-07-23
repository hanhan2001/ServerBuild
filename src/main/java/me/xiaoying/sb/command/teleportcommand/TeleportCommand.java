package me.xiaoying.sb.command.teleportcommand;

import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令 Teleport tp
 */
public class TeleportCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        new Thread(() -> {
            for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
                list.add(onlinePlayer.getName());

            if (sender instanceof Player) {
                Location location = ((Player) sender).getLocation();
                list.add(String.valueOf(location.getX()));
                list.add(String.valueOf(location.getY()));
                list.add(String.valueOf(location.getZ()));
            }
        }).start();
        return list;
    }
}