package me.xiaoying.serverbuild.script;

import me.xiaoying.serverbuild.script.interpreter.InterpreterManager;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Script manager
 */
public interface ScriptManager {
    /**
     * Register script
     *
     * @param script Script
     */
    void registerScript(Script script);

    /**
     * Unregister script by script's name
     *
     * @param script Script's name
     */
    void unregisterScript(String script);

    /**
     * Unregister script
     *
     * @param script Script
     */
    void unregisterScript(Script script);

    /**
     * Unregister all script
     */
    void unregisterScripts();

    /**
     * Perform script
     *
     * @param command full script command
     * @param sender Who sent this script command
     */
    void performScript(String command, CommandSender sender);

    /**
     * Get all registered scripts' name
     *
     * @return
     */
    List<String> getScripts();

    /**
     * Get script manager's interpreter manager
     *
     * @return InterpreterManager
     */
    InterpreterManager getInterpreterManager();
}