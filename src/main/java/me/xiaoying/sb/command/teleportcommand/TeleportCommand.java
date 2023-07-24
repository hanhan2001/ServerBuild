package me.xiaoying.sb.command.teleportcommand;

import me.xiaoying.sb.constant.TeleportConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.utils.NumUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;
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
        if (strings.length == 1 && !(sender instanceof Player)) {
            sender.sendMessage(new VariableFactory(TeleportConstant.MESSAGE_USEPLAYER)
                    .prefix(TeleportConstant.MESSAGE_PREFIX)
                    .date(TeleportConstant.SET_VARIABLE_DATEFORMAT)
                    .color()
                    .getString());
            return false;
        }

        // 命令参数判断
        Player senderPlayer = (Player) sender;
        Player toPlayer = null;
        String senderPlayerName = senderPlayer.getName();
        Location toLocation = null;
        if (strings.length == 1) {
            toPlayer = Bukkit.getPlayerExact(strings[0]);
        }
        if (strings.length == 2) {
            senderPlayer = Bukkit.getPlayerExact(strings[0]);
            senderPlayerName = strings[0];
            toPlayer = Bukkit.getPlayerExact(strings[1]);
        }
        if (strings.length == 3) {
            if (!NumUtil.isFloat(strings[0]) || !NumUtil.isFloat(strings[1]) || !NumUtil.isFloat(strings[2])) {
                TeleportConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(new VariableFactory(string)
                        .prefix(TeleportConstant.MESSAGE_PREFIX)
                        .date(TeleportConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString()));
                return false;
            }
            toLocation = new Location(senderPlayer.getLocation().getWorld(), Float.parseFloat(strings[0]), Float.parseFloat(strings[1]), Float.parseFloat(strings[2]));
        }
        if (strings.length > 3 || strings.length < 1) {
            TeleportConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(new VariableFactory(string)
                    .prefix(TeleportConstant.MESSAGE_PREFIX)
                    .date(TeleportConstant.SET_VARIABLE_DATEFORMAT)
                    .color()
                    .getString()));
            return false;
        }

        // tp玩家
        assert senderPlayer != null;

        if (strings.length == 1 || strings.length == 2) {
            // 判断是否找到玩家
            if (toPlayer == null) {
                sender.sendMessage(new VariableFactory(TeleportConstant.MESSAGE_NOTFOUNDPLAYER)
                                .prefix(TeleportConstant.MESSAGE_PREFIX)
                                .date(TeleportConstant.SET_VARIABLE_DATEFORMAT)
                                .player(senderPlayerName)
                                .placeholder(senderPlayer)
                                .color()
                                .getString());
                return false;
            }

            senderPlayer.teleport(toPlayer);
            for (String s1 : TeleportConstant.TP_PLAYER_TRIGGER) {
                senderPlayer.sendMessage(new VariableFactory(s1)
                            .prefix(TeleportConstant.MESSAGE_PREFIX)
                            .date(TeleportConstant.SET_VARIABLE_DATEFORMAT)
                            .player(toPlayer.getName())
                            .placeholder(senderPlayer)
                            .color()
                            .getString());
            }
            for (String s1 : TeleportConstant.TP_PLAYER_TRIGGERED) {
                toPlayer.sendMessage(new VariableFactory(s1)
                            .prefix(TeleportConstant.MESSAGE_PREFIX)
                            .date(TeleportConstant.SET_VARIABLE_DATEFORMAT)
                            .player(senderPlayer)
                            .placeholder(toPlayer)
                            .color()
                            .getString());
            }
            return false;
        }


        // tp坐标
        senderPlayer.teleport(toLocation);
        for (String s1 : TeleportConstant.TP_POS_TRIGGER) {
            senderPlayer.sendMessage(new VariableFactory(s1)
                        .prefix(TeleportConstant.MESSAGE_PREFIX)
                        .date(TeleportConstant.SET_VARIABLE_DATEFORMAT)
                        .player(senderPlayer)
                        .placeholder(senderPlayer)
                        .color()
                        .getString());
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<Player> list = new ArrayList<>(ServerUtil.getOnlinePlayers());

        if (strings.length == 1)
            return null;

        List<String> l = new ArrayList<>();
        for (Player player : list) {
            if (!player.getName().startsWith(strings[1]))
                continue;

            l.add(player.getName());
        }

        if (l.size() == 0)
            return null;

        return l;
    }
}