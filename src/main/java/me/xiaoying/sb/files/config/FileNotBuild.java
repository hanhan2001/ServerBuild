package me.xiaoying.sb.files.config;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileNotBuild {
    public static YamlConfiguration notBuild;

    public static boolean SET_ENABLE,
            SET_BUILD_ENABLE,
            SET_DESTRUCTION_ENABLE;

    public static String SET_DATEFORMAT;

    public static List<String> MESSAGE_BUILD,
            MESSAGE_DESTRUCTION,
            MESSAGE_HELP;

    public static List<String> BUILD_WORLDS, DESTRUCTION_WORLDS;

    public static String MESSAGE_PREFIX,
            MESSAGE_RELOAD,
            MESSAGE_NOPERMISSION;

    public static void fileNotBuild() {
        File notBuildFile = new File(ServerUtil.getDataFolder(), "NotBuild.yml");
        if (!notBuildFile.exists()) ServerUtil.saveResources("NotBuild.yml");
        notBuild = YamlConfiguration.loadConfiguration(notBuildFile);

        readConfig();
    }

    private static void readConfig() {
        SET_ENABLE = notBuild.getBoolean("Enable");
        SET_BUILD_ENABLE = notBuild.getBoolean("Build.Enable");
        SET_DESTRUCTION_ENABLE = notBuild.getBoolean("Destruction.Enable");
        SET_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : notBuild.getString("Set.DateFormat");

        MESSAGE_HELP = notBuild.getStringList("Use-Help");
        MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : notBuild.getString("Message.Prefix");
        MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_RELOAD : notBuild.getString("Message.Reload");
        MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_NOPERMISSION : notBuild.getString("Message.NoPermission");

        MESSAGE_BUILD = getStringList("Destruction.Message");
        MESSAGE_DESTRUCTION = getStringList("Build.Message");
        BUILD_WORLDS = notBuild.getStringList("Build.World");
        DESTRUCTION_WORLDS = notBuild.getStringList("Destruction.World");
    }

    private static List<String> getStringList(String path) {
        List<String> function;
        try {
            function = notBuild.getStringList(path);
        } catch (Exception e) {
            function = new ArrayList<>();
            function.add(notBuild.getString(path));
        }
        return function;
    }
}