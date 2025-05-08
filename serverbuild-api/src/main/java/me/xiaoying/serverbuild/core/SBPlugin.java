package me.xiaoying.serverbuild.core;

import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.file.FileManager;
import me.xiaoying.serverbuild.file.SimpleFileManager;
import me.xiaoying.serverbuild.gui.GuiManager;
import me.xiaoying.serverbuild.module.ModuleManager;
import me.xiaoying.serverbuild.module.SimpleModuleManager;
import me.xiaoying.serverbuild.pluginmanager.PluginManager;
import me.xiaoying.serverbuild.proxy.SProxyProvider;
import me.xiaoying.serverbuild.script.ScriptManager;
import me.xiaoying.sqlfactory.SqlFactory;
import me.xiaoying.sqlfactory.config.MysqlConfig;
import me.xiaoying.sqlfactory.config.SqliteConfig;
import me.xiaoying.sqlfactory.factory.MysqlFactory;
import me.xiaoying.sqlfactory.factory.SqliteFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SBPlugin {
    private static JavaPlugin instance;
    private static PluginManager pluginManager;
    private static ScriptManager scriptManager;
    private static GuiManager guiManager;
    private static FileManager fileManager;
    private static ModuleManager moduleManager;

    private static SProxyProvider proxyProvider;

    private static SqlFactory sqlFactory;

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2000);

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

        SBPlugin.proxyProvider = new SProxyProvider();
        SBPlugin.fileManager = new SimpleFileManager();
        SBPlugin.moduleManager = new SimpleModuleManager();

        // sqlfactory
        switch (ConfigCommon.SETTING_DATA_TYPE.toUpperCase(Locale.ENGLISH)) {
            default:
            case "SQLITE":
                File file = new File("./plugins/" + plugin.getName() + "/serverbuild.db");
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();

                SBPlugin.sqlFactory = new SqliteFactory(new SqliteConfig(file));
                break;
            case "MYSQL":
                SBPlugin.sqlFactory = new MysqlFactory(new MysqlConfig(ConfigCommon.SETTING_DATA_MYSQL_USERNAME,
                        ConfigCommon.SETTING_DATA_MYSQL_PASSWORD,
                        ConfigCommon.SETTING_DATA_MYSQL_HOST,
                        ConfigCommon.SETTING_DATA_MYSQL_PORT,
                        ConfigCommon.SETTING_DATA_MYSQL_DATABASE));
                break;
        }
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
     * Get java proxy
     *
     * @return SProxyProvider
     */
    public static SProxyProvider getProxyProvider() {
        return SBPlugin.proxyProvider;
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
     * Get Scheduler service at ServerBuild
     *
     * @return SchedulerExecutorService
     */
    public static ScheduledExecutorService getExecutorService() {
        return SBPlugin.executorService;
    }
}