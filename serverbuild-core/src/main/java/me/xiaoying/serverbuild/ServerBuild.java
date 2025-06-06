package me.xiaoying.serverbuild;

import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.serverbuild.ServerBuildCommand;
import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileConfig;
import me.xiaoying.serverbuild.gui.SimpleGuiManager;
import me.xiaoying.serverbuild.module.*;
import me.xiaoying.serverbuild.module.Module;
import me.xiaoying.serverbuild.pluginmanager.PaperPluginManager;
import me.xiaoying.serverbuild.pluginmanager.SpigotPluginManager;
import me.xiaoying.serverbuild.scheduler.PlaceholderScheduler;
import me.xiaoying.serverbuild.scheduler.Scheduler;
import me.xiaoying.serverbuild.script.SimpleScriptManager;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.sqlfactory.SqlFactory;
import me.xiaoying.sqlfactory.config.MysqlConfig;
import me.xiaoying.sqlfactory.config.SqliteConfig;
import me.xiaoying.sqlfactory.factory.MysqlFactory;
import me.xiaoying.sqlfactory.factory.SqliteFactory;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Main
 */
public class ServerBuild extends JavaPlugin {
    private static final List<SCommand> commands = new ArrayList<>();
    private static final List<Scheduler> scheduler = new ArrayList<>();

    @Override
    public void onEnable() {
        SBPlugin.setInstance(this);
        SBPlugin.setGuiManager(new SimpleGuiManager());

        ServerBuild.initialize();

        // bstats
        if (ConfigCommon.SETTING_BSTATS)
            new Metrics(SBPlugin.getInstance(), 16512);

        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|感谢您使用这个插件");
        ServerUtil.sendMessage("&b|任何问题可以添加QQ: &a764932129");
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&6功能状态:");
        List<Module> readyModules = new ArrayList<>();
        SBPlugin.getModuleManager().getModules().forEach(module -> {
            module.init();

            if (!module.ready()) {
                ServerUtil.sendMessage("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&c已关闭");
                module.disable();
                return;
            }
            readyModules.add(module);
            ServerUtil.sendMessage("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&e已开启");
        });
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&6全局配置状态:", true);
        if (ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE)
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &c未开启", true);
        if (ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE)
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &c未开启", true);
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");

        // enable modules
        readyModules.forEach(Module::enable);
    }

    @Override
    public void onDisable() {
        unInitialize();
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&c插件已卸载，感谢您的使用(乌拉！");
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
    }

    public static void initialize() {
        // initialize file
        FileConfig file = new FileConfig();
        SBPlugin.getFileManager().register(file);
        file.load();

        // init PluginManager
        switch (Bukkit.getServer().getName().toUpperCase(Locale.ENGLISH)) {
            case "PAPER":
                SBPlugin.setPluginManager(new PaperPluginManager());
                break;
            default:
                SBPlugin.setPluginManager(new SpigotPluginManager());
                break;
        }

        // init ScriptManager
        SBPlugin.setScriptManager(new SimpleScriptManager());

        // Command
        ServerBuildCommand serverBuildCommand = new ServerBuildCommand();
        ServerBuild.commands.add(serverBuildCommand);
        ServerBuild.commands.forEach(command -> command.getValues().forEach(string -> SBPlugin.getPluginManager().registerCommand(string, command.getTabExecutor(), SBPlugin.getInstance())));

        // SqlFactory
        SqlFactory sqlFactory;
        switch (ConfigCommon.SETTING_DATA_TYPE.toUpperCase(Locale.ENGLISH)) {
            default:
            case "SQLITE":
                File sqlite = new File("./plugins/" + SBPlugin.getInstance().getDescription().getName() + "/serverbuild.db");
                if (!sqlite.getParentFile().exists())
                    sqlite.getParentFile().mkdirs();

                sqlFactory = new SqliteFactory(new SqliteConfig(sqlite));
                break;
            case "MYSQL":
                sqlFactory = new MysqlFactory(new MysqlConfig(ConfigCommon.SETTING_DATA_MYSQL_USERNAME,
                        ConfigCommon.SETTING_DATA_MYSQL_PASSWORD,
                        ConfigCommon.SETTING_DATA_MYSQL_HOST,
                        ConfigCommon.SETTING_DATA_MYSQL_PORT,
                        ConfigCommon.SETTING_DATA_MYSQL_DATABASE));
                break;
        }
        SBPlugin.setSqlFactory(sqlFactory);

        // Module
        SBPlugin.getModuleManager().registerModule(new ChatFormatModule());
        SBPlugin.getModuleManager().registerModule(new AutoRespawnModule());
        SBPlugin.getModuleManager().registerModule(new FileMonitorModule());
        SBPlugin.getModuleManager().registerModule(new ResolveLagModule());
        SBPlugin.getModuleManager().registerModule(new WelcomeMessageModule());
        SBPlugin.getModuleManager().registerModule(new MessageAnnouncerModule());
//        SBPlugin.getModuleManager().registerModule(new PlayerEditModule());
        SBPlugin.getModuleManager().registerInterface(JavaModuleLoader.class);
        SBPlugin.getModuleManager().loadModules(SBPlugin.getInstance().getDataFolder().getParentFile());
        SBPlugin.getModuleManager().loadModules(new File(SBPlugin.getInstance().getDataFolder(), "modules"));
        SBPlugin.getModuleManager().unregisterInterfaces();

        // scheduler
        ServerBuild.scheduler.add(new PlaceholderScheduler());
        ServerBuild.scheduler.forEach(Scheduler::run);
    }

    public static void unInitialize() {
        // Command
        ServerBuild.commands.forEach(command -> command.getValues().forEach(string -> SBPlugin.getPluginManager().unregisterCommand(string, SBPlugin.getInstance())));
        ServerBuild.commands.clear();

        // Module
        SBPlugin.getModuleManager().disableModules();
        SBPlugin.getModuleManager().unregisterModules();

        // unregister manager
        ((SimpleGuiManager) SBPlugin.getGuiManager()).unInitialize();

        // Scheduler
        ServerBuild.scheduler.forEach(Scheduler::stop);
        ServerBuild.scheduler.clear();
    }
}