package me.xiaoying.serverbuild.script;

import org.bukkit.command.CommandSender;

/**
 * Script
 */
public interface Script {
    /**
     * Get script's name<br>
     *
     * @return Script's name
     */
    String getName();

    /**
     * Perform command
     *
     * @param sender Who sent this script
     * @param args parameters
     */
    void performCommand(CommandSender sender, String[] args);

    /**
     * Determine is script need run before interpreter
     *
     * @return Boolean
     */
    boolean processFirst();
}