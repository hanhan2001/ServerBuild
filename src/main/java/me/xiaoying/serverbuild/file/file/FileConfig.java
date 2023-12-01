package me.xiaoying.serverbuild.file.file;

import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * File Config
 */
public class FileConfig implements SubFile {
    File file = new File(ServerUtil.getDataFolder(), "Config.yml");
    YamlConfiguration yamlConfiguration;

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public String getPath() {
        return this.file.getAbsolutePath();
    }

    @Override
    public File getDataFolder() {
        return ServerUtil.getDataFolder();
    }

    @Override
    public YamlConfiguration getYamlConfiguration() {
        return this.yamlConfiguration;
    }

    @Override
    public void file() {
        if (!this.file.exists()) ServerUtil.saveResources("Config.yml");
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void initialize() {
        ConstantCommon.SYSTEM_ENABLE_BSATAS = yamlConfiguration.getBoolean("Set.Bstats");
        ConstantCommon.SYSTEM_ENABLE_OVERALL = yamlConfiguration.getBoolean("OverallSituation.Enable");
        ConstantCommon.SYSTEM_ENABLE_OVERALL_VARIABLE = yamlConfiguration.getBoolean("OverallSituation.Variable.Enable");
        ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE = yamlConfiguration.getBoolean("OverallSituation.Message.Enable");

        // 数据存储
        ConstantCommon.SYSTEM_DATA_TYPE = yamlConfiguration.getString("Set.Data.Type");
        if (StringUtil.isEmpty(ConstantCommon.SYSTEM_DATA_TYPE)) {
            ConstantCommon.SYSTEM_DATA_TYPE = "SQLite";
            ServerUtil.sendMessage("&e指定 数据存储 方式为空，将默认设置为 SQLite", true);
        }
        if (!ConstantCommon.SYSTEM_DATA_TYPE.equalsIgnoreCase("SQLite") && !ConstantCommon.SYSTEM_DATA_TYPE.equalsIgnoreCase("Mysql")) {
            ConstantCommon.SYSTEM_DATA_TYPE = "SQLite";
            ServerUtil.sendMessage("&e指定 数据存储 方式不存在(可选 Mysql/SQLite)，将默认设置为 SQLite", true);
        }
        ConstantCommon.SYSTEM_DATA_SQLITE_DATAPATH = yamlConfiguration.getString("Set.Data.SQLite.DataPath");
        if (ConstantCommon.SYSTEM_DATA_TYPE.equalsIgnoreCase("SQLite") && StringUtil.isEmpty(ConstantCommon.SYSTEM_DATA_SQLITE_DATAPATH)) {
            ConstantCommon.SYSTEM_DATA_SQLITE_DATAPATH = "plugins/ServerBuild/ServerBuild.db";
            ServerUtil.sendMessage("&e选用 SQLite 作为数据存储方式，但未设置存储文件位置，将默认设置为 plugins/ServerBuild/ServerBuild.db", true);
        }
        ConstantCommon.SYSTEM_MYSQL_HOST = yamlConfiguration.getString("Set.Data.Mysql.Host");
        ConstantCommon.SYSTEM_MYSQL_DATABASE = yamlConfiguration.getString("Set.Data.Mysql.Database");
        ConstantCommon.SYSTEM_MYSQL_USERNAME = yamlConfiguration.getString("Set.Data.Mysql.Username");
        ConstantCommon.SYSTEM_MYSQL_PASSWORD = yamlConfiguration.getString("Set.Data.Mysql.Password");
        ConstantCommon.SYSTEM_MYSQL_PORT = yamlConfiguration.getInt("Set.Data.Mysql.Port");

        ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT = yamlConfiguration.getString("OverallSituation.Variable.DateFormat");
        ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX = yamlConfiguration.getString("OverallSituation.Variable.Prefix");
        ConstantCommon.SYSTEM_OVERALL_MESSAGE_RELOAD = yamlConfiguration.getString("OverallSituation.Message.Reload");
        ConstantCommon.SYSTEM_OVERALL_MESSAGE_COMPLETE = yamlConfiguration.getString("OverallSituation.Message.Complete");
        ConstantCommon.SYSTEM_OVERALL_MESSAGE_NOPERMISSION = yamlConfiguration.getString("OverallSituation.Message.NoPermission");

        ConstantCommon.SYSTEM_OVERALL_MESSAGE_HELP = yamlConfiguration.getStringList("OverallSituation.Message.Help");
    }

    @Override
    public void delete() {
        this.file.delete();
    }

    @Override
    public ConstantCommon getConstant() {
        return null;
    }
}