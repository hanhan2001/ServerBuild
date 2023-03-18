package me.xiaoying.sb.command;

import org.bukkit.command.CommandSender;

/**
 * 命令 接口模板
 */
public abstract class SubCommand {
    public abstract boolean performCommand(CommandSender sender, String[] args);
}