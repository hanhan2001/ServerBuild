package me.xiaoying.sb.file.files;

import me.xiaoying.mf.SqlFactory;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.HomeConstant;
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
        HomeConstant.SET_LIMIT_WORLD_ENABLE = home.getBoolean("Enable");
        HomeConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Set.DateFormat");
        HomeConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Message.Prefix");
        HomeConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Message.Reload");
        HomeConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : home.getString("Message.NoPermission");
        HomeConstant.MESSAGE_ALREADY_EXISTS = home.getString("Message.AlreadyExists");
        HomeConstant.MESSAGE_WORLD_NOTEXISTS = home.getString("Message.WorldNotExists");
        HomeConstant.MESSAGE_ERROR_IDENTITY = home.getString("Message.ErrorIdentity");
        HomeConstant.MESSAGE_SET_SUCCESS = home.getString("Message.SetSuccess");
        HomeConstant.MESSAGE_OVER_LIMIT = home.getString("Message.OverLimit");
        HomeConstant.MESSAGE_HOME_NOT_EXISTS = home.getString("Message.HomeNotExists");

        HomeConstant.MESSAGE_HELP = home.getStringList("Use-Help");
        HomeConstant.HOME_GROUPS = home.getStringList("Home.Groups");
        HomeConstant.LIMIT_WORLD = home.getStringList("WorldLimits.World");
        HomeConstant.HOME_NAME_REGEX = home.getString("Homes.HomeNameRegex");
    }
}
