package me.xiaoying.serverbuild;

import me.xiaoying.serverbuild.command.serverbuild.ServerBuildCommand;
import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.file.FileService;
import me.xiaoying.serverbuild.function.Function;
import me.xiaoying.serverbuild.function.FunctionService;
import me.xiaoying.serverbuild.file.file.FileConfig;
import me.xiaoying.serverbuild.function.functions.AutoReSpawnFunction;
import me.xiaoying.serverbuild.function.functions.ChatFormatFunction;
import me.xiaoying.serverbuild.function.functions.FileMonitorFunction;
import me.xiaoying.serverbuild.script.ScriptManager;
import me.xiaoying.serverbuild.task.SubTask;
import me.xiaoying.serverbuild.task.TaskService;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.sql.MysqlFactory;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.SqliteFactory;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 插件主类
 */
public class ServerBuild extends JavaPlugin {
    private static ServerBuild instance;
    private static FileService fileService;
    private static TaskService taskService;
    private static ScriptManager scriptManager;
    private static FunctionService functionService;

    @Override
    public void onEnable() {
        instance = this;

        initialize();
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|感谢您使用这个插件", true);
        ServerUtil.sendMessage("&b|任何问题可以添加QQ: &a764932129", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&6功能状态:", true);
        loadFunction();
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&6全局配置状态:", true);
        if (ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE)
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &c未开启", true);
        if (ConstantCommon.SYSTEM_ENABLE_OVERALL_VARIABLE)
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &c未开启", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&6基本信息:", true);
        if (ConstantCommon.SYSTEM_ENABLE_BSATAS) {
            new Metrics(this, 16512);
            ServerUtil.sendMessage("&b|    &a统计信息(Bstats): &e已开启", true);
        } else
            ServerUtil.sendMessage("&b|    &a统计信息(Bstats): &c未开启", true);
        ServerUtil.sendMessage("&b|    &a数据存储方式(DataType): &e" + ConstantCommon.SYSTEM_DATA_TYPE, true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);

        runFunction();
    }

    @Override
    public void onDisable() {
        unInitialize();

        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&c插件已卸载，感谢您的使用(乌拉！", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=->", true);
    }

    // 初始化
    public static void initialize() {
        fileService = new FileService();
        taskService = new TaskService();
        scriptManager = new ScriptManager();
        functionService = new FunctionService();

        fileService.register("Config", new FileConfig());

        functionService.registerFunction(new ChatFormatFunction());
        functionService.registerFunction(new AutoReSpawnFunction());
        functionService.registerFunction(new FileMonitorFunction());

        fileService.fileAll();
        fileService.initializeAll();

        // 注册主指令
        ServerUtil.registerCommand("sb", new ServerBuildCommand());
    }

    // 取消初始化
    public static void unInitialize() {
        for (SubTask subTask : taskService.getTasks())
            subTask.stop();
    }

    // 加载 Function
    public static void loadFunction() {
        for (Function function : functionService.getFunctions()) {
            if (!function.enable()) {
                ServerUtil.sendMessage("&b|    &a" + function.getAliasName() + "(" + function.getName() + "): &c未开启", true);
                continue;
            }

            function.onDisable();
            ServerUtil.sendMessage("&b|    &a" + function.getAliasName() + "(" + function.getName() + "): &e已开启", true);
        }
    }

    // 启动 Function
    public static void runFunction() {
        // Function 处理
        functionService.enableFunctions();
        for (SubTask subTask : taskService.getTasks()) {
            if (subTask.isRunning())
                continue;

            subTask.run();
        }
    }

    // 获取 FileService
    public static FileService getFileService() {
        return fileService;
    }

    // 获取 TaskService
    public static TaskService getTaskService() {
        return taskService;
    }

    // 获取 ScriptManager
    public static ScriptManager getScriptManager() {
        return scriptManager;
    }

    // 获取 FunctionService
    public static FunctionService getFunctionService() {
        return functionService;
    }

    // 获取数据存储库
    public static SqlFactory getSqlFactory() {
        SqlFactory sqlFactory = null;
        switch (ConstantCommon.SYSTEM_DATA_TYPE.toUpperCase()) {
            case "MYSQL":
                sqlFactory = new MysqlFactory(ConstantCommon.SYSTEM_MYSQL_HOST, ConstantCommon.SYSTEM_MYSQL_PORT, ConstantCommon.SYSTEM_MYSQL_DATABASE, ConstantCommon.SYSTEM_MYSQL_USERNAME, ConstantCommon.SYSTEM_MYSQL_PASSWORD);
                break;
            case "SQLITE":
                sqlFactory = new SqliteFactory("jdbc:sqlite", ConstantCommon.SYSTEM_DATA_SQLITE_DATAPATH);
                break;
        }
        return sqlFactory;
    }

    // 插件接口
    public static ServerBuild getInstance() {
        return instance;
    }
}