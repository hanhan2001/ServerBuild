package me.xiaoying.sb.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * 命令 接口模板
 */
public abstract class SubCommand {
    public abstract void registerCommand(SubCommand command);

    public abstract boolean performCommand(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args);
}