package me.xiaoying.sb.files.config;

import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * 文件 WelcomeMessage.yml
 */
public class FileWelcomeMessage {
    public static YamlConfiguration welcomeMessage;

    public static boolean SET_ENABLE;

    public static String MESSAGE_PREFIX,
            MESSAGE_RELOAD,
            MESSAGE_NOPERMISSION,
            SET_VARIABLE_DATEFORMAT;

    public static List<String> MESSAGE_HELP;

    public static void fileWelcomeMessage() {
        File welcomeMessageFile = new File(ServerUtil.getDataFolder(), "WelcomeMessage.yml");
        if (!welcomeMessageFile.exists()) ServerUtil.saveResources("WelcomeMessage.yml");
        welcomeMessage = YamlConfiguration.loadConfiguration(welcomeMessageFile);

        readConfig();
    }

    private static void readConfig() {
        SET_ENABLE = welcomeMessage.getBoolean("Enable");
        MESSAGE_PREFIX = FileConfig.OVERALL_ENABLE && FileConfig.OVERALL_ENABLE_MESSAGE ? FileConfig.OVERALL_MESSAGE_PREFIX : welcomeMessage.getString("Message.Prefix");
        MESSAGE_RELOAD = FileConfig.OVERALL_ENABLE && FileConfig.OVERALL_ENABLE_MESSAGE ? FileConfig.OVERALL_MESSAGE_RELOAD : welcomeMessage.getString("Message.Reload");
        SET_VARIABLE_DATEFORMAT = FileConfig.OVERALL_ENABLE && FileConfig.OVERALL_ENABLE_VARIABLE ? FileConfig.OVERALL_VARIABLE_DATAFORMAT : welcomeMessage.getString("Set.DateFormat");
        MESSAGE_NOPERMISSION = FileConfig.OVERALL_ENABLE && FileConfig.OVERALL_ENABLE_MESSAGE ? FileConfig.OVERALL_MESSAGE_NOPERMISSION : welcomeMessage.getString("Message.NoPermission");

        MESSAGE_HELP = welcomeMessage.getStringList("Use-Help");
    }
}