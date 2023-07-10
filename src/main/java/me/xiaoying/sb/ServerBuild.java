package me.xiaoying.sb;

import me.xiaoying.sb.command.admincommand.AdminCommand;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.file.FileService;
import me.xiaoying.sb.file.files.*;
import me.xiaoying.sb.handle.*;
import me.xiaoying.sb.handle.handls.*;
import me.xiaoying.sb.listener.ListenerService;
import me.xiaoying.sb.service.scriptcommand.ScriptCommandService;
import me.xiaoying.sb.task.TaskService;
import me.xiaoying.sb.utils.ServerUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 插件主类
 */
public class ServerBuild extends JavaPlugin {
    private static ServerBuild instance;
    private static final TaskService taskService = new TaskService();
    private static final FileService fileService = new FileService();
    private static final ListenerService listenerService = new ListenerService();
    private static final ScriptCommandService scriptCommandService = new ScriptCommandService();

    @Override
    public void onEnable() {
        instance = this;
        // 判断是否拥有前置
        // 是 跳过
        // 否 卸载插件
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            ServerUtil.sendMessage("&c未安装前置: PlaceholderAPI", true);
            this.onDisable();
            return;
        }

        // 初始化数据
        initialize();
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|感谢您使用这个插件", true);
        ServerUtil.sendMessage("&b|任何问题可以添加QQ: &a764932129", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&6功能状态:", true);
        loadHandle();
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&6全局配置状态:", true);
        if (ConfigConstant.OVERALL_ENABLE_MESSAGE)
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &c未开启", true);
        if (ConfigConstant.OVERALL_ENABLE_VARIABLE)
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &c未开启", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
    }

    @Override
    public void onDisable() {
        clear();

        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&c插件已卸载，感谢您的使用(乌拉！", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=->", true);
    }

    private void loadHandle() {
        Handler.registerHandle("LoginTP", new LoginTPHandle());
        Handler.registerHandle("NotBuild", new NotBuildHandle());
        Handler.registerHandle("ChatFormat", new ChatFormatHandle());
        Handler.registerHandle("AutoReSpawn", new AutoReSpawnHandle());
        Handler.registerHandle("FileMonitor", new FileMonitorHandle());
        Handler.registerHandle("WelcomeMessage", new WelcomeMessageHandle());
        Handler.registerHandle("MessageAnnouncer", new MessageAnnouncerHandle());

        Handler.loadHandles();
    }

    // 初始化
    public static void initialize() {
        // 检测插件使用量
        new Metrics(ServerBuild.getInstance(), 16512);
        scriptCommandService.registerCommands();

        // 初始化配置文件
        fileService.register("Config", new FileConfig());
        fileService.register("LoginTP", new FileLoginTp());
        fileService.register("NotBuild", new FileNotBuild());
        fileService.register("Teleport", new FileTeleport());
        fileService.register("ChatFormat", new FileChatFormat());
        fileService.register("AutoReSpawn", new FileAutoReSpawn());
        fileService.register("FileMonitor", new FileFileMonitor());
        fileService.register("WelcomeMessage", new FileWelcomeMessage());
        fileService.register("MessageAnnouncer", new FileMessageAnnouncer());
        fileService.fileAll();
        fileService.initAll();

        ServerUtil.registerCommand("sb", new AdminCommand());
    }

    // 格式化
    public static void clear() {
        // 关闭 Function
        Handler.stopHandles();

        // 关闭线程
        taskService.stopTasks();

        // 关闭事件
        listenerService.stopListeners();
    }

    public static TaskService getTaskServer() {
        return taskService;
    }

    public static FileService getFileService() {
        return fileService;
    }

    public static ListenerService getListenerService() {
        return listenerService;
    }

    public static ScriptCommandService getScriptCommandService() {
        return scriptCommandService;
    }

    public static ServerBuild getInstance() {
        return instance;
    }
}