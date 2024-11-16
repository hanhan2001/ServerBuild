package me.xiaoying.serverbuild.core;

import me.xiaoying.serverbuild.file.FileManager;
import me.xiaoying.serverbuild.file.SimpleFileManager;
import me.xiaoying.serverbuild.gui.GuiManager;
import me.xiaoying.serverbuild.module.ModuleManager;
import me.xiaoying.serverbuild.module.SimpleModuleManager;
import me.xiaoying.serverbuild.pluginmanager.PluginManager;
import me.xiaoying.serverbuild.script.ScriptManager;
import me.xiaoying.sql.SqlFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class SBPlugin {
    private static JavaPlugin instance;
    private static PluginManager pluginManager;
    private static ScriptManager scriptManager;
    private static GuiManager guiManager;
    private static FileManager fileManager;
    private static ModuleManager moduleManager;
    private static SqlFactory sqlFactory;

    public static JavaPlugin getInstance() {
        return SBPlugin.instance;
    }

    /**
     * Set plugin instance
     *
     * @param plugin JavaPlugin
     */
    public static void setInstance(JavaPlugin plugin) {
        SBPlugin.instance = plugin;

        SBPlugin.fileManager = new SimpleFileManager();
        SBPlugin.moduleManager = new SimpleModuleManager();
    }

    /**
     * Get manager of plugin
     *
     * @return PluginManager
     */
    public static PluginManager getPluginManager() {
        return SBPlugin.pluginManager;
    }

    /**
     * Set manager of plugin
     *
     * @param pluginManager PluginManager
     */
    public static void setPluginManager(PluginManager pluginManager) {
        if (SBPlugin.pluginManager != null)
            return;

        SBPlugin.pluginManager = pluginManager;
    }

    /**
     * Get manager of script
     *
     * @return ScriptManager
     */
    public static ScriptManager getScriptManager() {
        return SBPlugin.scriptManager;
    }

    /**
     * Set manager of script
     *
     * @param scriptManager ScriptManager
     */
    public static void setScriptManager(ScriptManager scriptManager) {
        if (SBPlugin.scriptManager != null)
            return;

        SBPlugin.scriptManager = scriptManager;
    }

    /**
     * Get manager of module
     *
     * @return ModuleManager
     */
    public static ModuleManager getModuleManager() {
        return SBPlugin.moduleManager;
    }

    /**
     * Set manager of module
     *
     * @param moduleManager ModuleManager
     */
    public static void setModuleManager(ModuleManager moduleManager) {
        if (SBPlugin.moduleManager != null)
            return;

        SBPlugin.moduleManager = moduleManager;
    }

    /**
     * Get gui manager
     *
     * @return GuiManager
     */
    public static GuiManager getGuiManager() {
        return SBPlugin.guiManager;
    }

    /**
     * Set manager of gui
     *
     * @param guiManager GuiManager
     */
    public static void setGuiManager(GuiManager guiManager) {
        SBPlugin.guiManager = guiManager;
    }

    /**
     * Get manager of file
     *
     * @return FileManager
     */
    public static FileManager getFileManager() {
        return fileManager;
    }

    /**
     * Set manager of file
     *
     * @param fileManager FileManager
     */
    public static void setFileManager(FileManager fileManager) {
        if (SBPlugin.fileManager != null)
            return;

        SBPlugin.fileManager = fileManager;
    }

    /**
     * Get SqlFactory
     *
     * @return SqlFactory
     */
    public static SqlFactory getSqlFactory() {
        return SBPlugin.sqlFactory;
    }

    /**
     * Set SqlFactory
     *
     * @param sqlFactory SqlFactory
     */
    public static void setSqlFactory(SqlFactory sqlFactory) {
        SBPlugin.sqlFactory = sqlFactory;
    }
}