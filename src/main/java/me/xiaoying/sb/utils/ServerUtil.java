package me.xiaoying.sb.utils;

import me.xiaoying.sb.ServerBuild;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.Collection;
import java.util.Objects;

/**
 * 工具类 服务器
 */
public class ServerUtil {
    /**
     * 发送控制台消息
     *
     * @param message 消息内容
     * @param translate 是否使用彩色
     */
    public static void sendMessage(String message, boolean translate) {
        if (translate) {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.translate(message));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(message);
    }

    /**
     * 注册游戏指令
     *
     * @param command 指令
     * @param commandExecutor 指令对象
     */
    public static void registerCommand(String command, CommandExecutor commandExecutor) {
        PluginUtil.registerCommand(command, ServerBuild.getInstance());
        Objects.requireNonNull(ServerBuild.getInstance().getCommand(command)).setExecutor(commandExecutor);
    }

    /**
     * 注册游戏事件
     *
     * @param listener 事件对象
     */
    public static void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, ServerBuild.getInstance());
    }

    /**
     * 获取插件运行路径
     *
     * @return 路径
     */
    public static File getDataFolder() {
        return ServerBuild.getInstance().getDataFolder();
    }

    /**
     * 执行命令
     *
     * @param command 命令
     */
    public static void dispatchCommand(String command) {
        ServerBuild.getInstance().getServer().dispatchCommand(ServerBuild.getInstance().getServer().getConsoleSender(), command);
    }

    /**
     * 获取服务器版本
     *
     * @return 服务器版本
     */
    public static String getServerVersion() {
        return ServerBuild.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    /**
     * 保存文件
     *
     * @param file 文件名称
     */
    public static void saveResources(String file) {
        ServerBuild.getInstance().saveResource(file, false);
    }


    /**
     * 获取世界
     *
     * @param world 世界名称
     * @return 世界
     */
    public static World getWorld(String world) {
        return Bukkit.getServer().getWorld(world);
    }

    /**
     * 在线玩家发送消息
     *
     * @param message 消息
     */
    public static void onlinePlayersSendMessage(String message) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            PlayerUtil.sendMessage(player, message);
        });
    }

    /**
     * 在线玩家发送Title
     *
     * @param title 主标题
     * @param subtitle 副标题
     */
    public static void onlinePlayersSendTitle(String title, String subtitle) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            PlayerUtil.sendTitle(player, title, subtitle);
        });
    }

    /**
     * 在线玩家发送ActionBar
     *
     * @param message 内容
     */
    public static void onlinePlayersSendActionbar(String message) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            PlayerUtil.sendActionbar(player, message);
        });
    }

    /**
     * 获取服务器所有在线玩家
     *
     * @return 玩家列表
     */
    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getServer().getOnlinePlayers();
    }
}