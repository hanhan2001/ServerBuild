package me.xiaoying.serverbuild.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Command
 */
public interface SubCommand {
    void registerCommand(SubCommand command);

    void performCommand(CommandSender sender, String[] args);

    List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args);
}