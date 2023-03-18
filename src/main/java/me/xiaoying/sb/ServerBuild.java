package me.xiaoying.sb;

import me.xiaoying.sb.files.FileManager;
import me.xiaoying.sb.files.config.FileConfig;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.handle.LoginTPHandle;
import me.xiaoying.sb.handle.NotBuildHandle;
import me.xiaoying.sb.utils.ServerUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 插件主类
 */
public class ServerBuild extends JavaPlugin {
    private static ServerBuild instance;

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
        if (FileConfig.OVERALL_ENABLE_MESSAGE)
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &c未开启", true);
        if (FileConfig.OVERALL_ENABLE_VARIABLE)
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &c未开启", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->", true);

//        ServerUtil.sendMessage("&b|    &a聊天格式(ChatFormat)： &e已开启", true);
    }

    @Override
    public void onDisable() {
        clear();

        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=->", true);
        ServerUtil.sendMessage("&b|&c插件已卸载，感谢您的使用(乌拉！", true);
        ServerUtil.sendMessage("&b|=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=->", true);
    }

    private void loadHandle() {
        Handler.registerHandle("NotBuild", new NotBuildHandle());
        Handler.registerHandle("LoginTP", new LoginTPHandle());
    }

    // 初始化
    public static void initialize() {
        // 检测插件使用量
        new Metrics(ServerBuild.getInstance(), 16512);

        // 初始化配置文件
        FileManager.fileManager();


    }

    // 格式化
    public static void clear() {

    }

    public static ServerBuild getInstance() {
        return instance;
    }
}