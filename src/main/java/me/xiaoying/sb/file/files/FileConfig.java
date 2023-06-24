package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileConfig extends SubFile {
    public static YamlConfiguration config;
    File file = new File(ServerUtil.getDataFolder(), "Config.yml");

    @Override
    public void newFile() {
        if (!this.file.exists()) ServerUtil.saveResources("Config.yml");
        config = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        file.delete();
    }

    @Override
    public void initFile() {
        ConfigConstant.SET_BSTATS = config.getBoolean("Set.Bstats");
        ConfigConstant.OVERALL_ENABLE = config.getBoolean("Enable");
        ConfigConstant.OVERALL_ENABLE_MESSAGE = config.getBoolean("Message.Enable");
        ConfigConstant.OVERALL_ENABLE_VARIABLE = config.getBoolean("Variable.Enable");

        ConfigConstant.OVERALL_VARIABLE_DATAFORMAT = config.getString("Variable.DateFormat");

        ConfigConstant.OVERALL_MESSAGE_RELOAD = config.getString("Message.Reload");
        ConfigConstant.OVERALL_MESSAGE_PREFIX = config.getString("Message.Prefix");
        ConfigConstant.OVERALL_MESSAGE_NOPERMISSION = config.getString("Message.NoPermission");

        ConfigConstant.MESSAGE_HELP = config.getStringList("Use-Help");
    }
}