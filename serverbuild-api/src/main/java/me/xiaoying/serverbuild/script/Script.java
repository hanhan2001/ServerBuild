package me.xiaoying.serverbuild.script;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Script
 */
public abstract class Script {
    private final List<Script> scripts = new ArrayList<>();

    /**
     * Register script as sub script of this command
     *
     * @param script Script
     */
    public void registerScript(Script script) {
        if (this.scripts.contains(script))
            return;

        this.scripts.add(script);
    }

    /**
     * Get registered sub scripts
     *
     * @return ArrayList
     */
    public List<Script> getScripts() {
        return this.scripts;
    }

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