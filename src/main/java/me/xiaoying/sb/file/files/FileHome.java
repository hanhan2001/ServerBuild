package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.constant.NotBuildConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileHome extends SubFile {
    public static YamlConfiguration home;
    File file = new File(ServerUtil.getDataFolder(), "Home.yml");

    @Override
    public void newFile() {
        if (!this.file.exists()) ServerUtil.saveResources("Home.yml");
        home = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        file.delete();
    }

    @Override
    public void initFile() {
        HomeConstant.SET_ENABLE = home.getBoolean("Enable");
        HomeConstant.SET_WORLDLIMITS_ENABLE = home.getBoolean("Enable");
        HomeConstant.SET_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Set.DateFormat");
        HomeConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Message.Prefix");
        HomeConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Message.Reload");
        HomeConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Message.NoPermission");
        HomeConstant.MESSAGE_HELP = home.getStringList("Use-Help");
        HomeConstant.HOME_GROUPS = home.getStringList("Home.Groups");
        HomeConstant.WORLDLIMITS_WORLD = home.getStringList("WorldLimits.World");
        HomeConstant.HOMENAMEREGEX = home.getString("Regex");


    }
}
