package me.xiaoying.sb.files.config;

import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * 配置文件 Config.yml
 */
public class FileConfig {
    public static YamlConfiguration config;

    /**
     * SET_BSTATS 是否上传使用插件数据
     * OVERALL_ENABLE 是否开启全局配置
     * OVERALL_ENABLE_VARIABLE 是否开启全局变量
     * OVERALL_ENABLE_MESSAGE 是否开启全局词条
     */
    public static Boolean SET_BSTATS,
            OVERALL_ENABLE,
            OVERALL_ENABLE_VARIABLE,
            OVERALL_ENABLE_MESSAGE;

    /**
     * OVERALL_VARIABLE_DATAFORMAT 全局变量 日期
     * OVERALL_MESSAGE_PREFIX 全局词条 前缀
     * OVERALL_MESSAGE_NOPERMISSION 全局词条 无权限
     */
    public static String OVERALL_VARIABLE_DATAFORMAT,
            OVERALL_MESSAGE_PREFIX,
            OVERALL_MESSAGE_RELOAD,
            OVERALL_MESSAGE_NOPERMISSION;

    public static void fileConfig() {
        File configFile = new File(ServerUtil.getDataFolder(), "Config.yml");
        if (!configFile.exists()) ServerUtil.saveResources("Config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        readConfig();
    }

    private static void readConfig() {
        SET_BSTATS = config.getBoolean("Set.Bstats");
        OVERALL_ENABLE = config.getBoolean("Enable");
        OVERALL_ENABLE_MESSAGE = config.getBoolean("Message.Enable");
        OVERALL_ENABLE_VARIABLE = config.getBoolean("Variable.Enable");

        OVERALL_VARIABLE_DATAFORMAT = config.getString("Variable.DateFormat");

        OVERALL_MESSAGE_RELOAD = config.getString("Message.Reload");
        OVERALL_MESSAGE_PREFIX = config.getString("Message.Prefix");
        OVERALL_MESSAGE_NOPERMISSION = config.getString("Message.NoPermission");
    }
}