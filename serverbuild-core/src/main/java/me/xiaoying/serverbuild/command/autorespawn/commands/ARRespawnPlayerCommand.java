package me.xiaoying.serverbuild.command.autorespawn.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(values = "respawn", length = 1)
public class ARRespawnPlayerCommand extends SCommand {
    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!sender.isOp() && !ServerUtil.hasPermission(sender, "sb.admin", "sb.ar.admin")) {
            sender.sendMessage(new VariableFactory(FileAutoRespawn.MESSAGE_MISSING_PERMISSION).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).color().toString());
            return;
        }

        Player player;
        if ((player = Bukkit.getServer().getPlayer(args[0])) == null) {
            sender.sendMessage(new VariableFactory(FileAutoRespawn.MESSAGE_NOT_FOUND_PLAYER).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).placeholder(sender).color().toString());
            return;
        }

        if (player.isDead())
            player.spigot().respawn();

        sender.sendMessage(new VariableFactory(FileAutoRespawn.MESSAGE_COMPLETE).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).placeholder(sender).color().toString());
    }
}