package me.xiaoying.sb.files.config;

import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * 配置文件 LoginTp.yml
 */
public class FileLoginTp {
    public static YamlConfiguration loginTP;

    public static boolean SET_ENABLE;

    public static World LOCATION_WORLD;

    public static double LOCATION_X,
            LOCATION_Y,
            LOCATION_Z;

    public static float LOCATION_YAW,
            LOCATION_PITCH;

    public static String MESSAGE_PREFIX,
            MESSAGE_RELOAD,
            MESSAGE_NOPERMISSION,
            MESSAGE_USEPLAYER;

    public static List<String> MESSAGE_HELP;

    public static void fileConfig() {
        File configFile = new File(ServerUtil.getDataFolder(), "LoginTP.yml");
        if (!configFile.exists()) ServerUtil.saveResources("LoginTP.yml");
        loginTP = YamlConfiguration.loadConfiguration(configFile);

        readConfig();
    }

    private static void readConfig() {
        SET_ENABLE = loginTP.getBoolean("Enable");

        LOCATION_WORLD = ServerUtil.getWorld(loginTP.getString("Location.World"));
        LOCATION_X = loginTP.getDouble("Location.X");
        LOCATION_Y = loginTP.getDouble("Location.Z");
        LOCATION_Z = loginTP.getDouble("Location.Y");
        LOCATION_YAW = (float) loginTP.getDouble("Location.Yaw");
        LOCATION_PITCH = (float) loginTP.getDouble("Location.Pitch");

        MESSAGE_PREFIX = FileConfig.OVERALL_ENABLE && FileConfig.OVERALL_ENABLE_MESSAGE ? FileConfig.OVERALL_MESSAGE_PREFIX : loginTP.getString("Message.Prefix");
        MESSAGE_RELOAD = FileConfig.OVERALL_ENABLE && FileConfig.OVERALL_ENABLE_MESSAGE ? FileConfig.OVERALL_MESSAGE_RELOAD : loginTP.getString("Message.Reload");
        MESSAGE_USEPLAYER = loginTP.getString("Message.UsePlayer");
        MESSAGE_NOPERMISSION = FileConfig.OVERALL_ENABLE && FileConfig.OVERALL_ENABLE_MESSAGE ? FileConfig.OVERALL_MESSAGE_NOPERMISSION : loginTP.getString("Message.NoPermission");

        MESSAGE_HELP = loginTP.getStringList("Use-Help");
    }
}