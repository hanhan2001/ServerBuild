package me.xiaoying.serverbuild.script;

import org.bukkit.command.CommandSender;

/**
 * Script
 */
public abstract class Script {
    /**
     * Get script's name<br>
     *
     * @return Script's name
     */
    public abstract String getName();

    /**
     * Perform command
     *
     * @param sender Who sent this script
     * @param args parameters
     */
    public abstract void performCommand(CommandSender sender, String[] args);

    /**
     * Determine is script need run before interpreter
     *
     * @return Boolean
     */
    public abstract boolean processFirst();
}