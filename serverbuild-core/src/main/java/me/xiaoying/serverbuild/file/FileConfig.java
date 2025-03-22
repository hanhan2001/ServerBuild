package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.common.ConfigCommon;

/**
 * File Config.yml
 */
public class FileConfig extends SFile {
    public FileConfig() {
        super("Config.yml");
    }

    @Override
    public void onLoad() {
        ConfigCommon.SETTING_BSTATS = this.getConfiguration().getBoolean("Setting.Bstats");
        ConfigCommon.SETTING_DATA_TYPE = this.getConfiguration().getString("Setting.Data.Type");
        ConfigCommon.SETTING_DATA_SQLITE_DATAPATH = this.getConfiguration().getString("Setting.Data.SQLite.DataPath");
        ConfigCommon.SETTING_DATA_MYSQL_HOST = this.getConfiguration().getString("Setting.Data.Mysql.Host");
        ConfigCommon.SETTING_DATA_MYSQL_PORT = this.getConfiguration().getInt("Setting.Data.Mysql.Port");
        ConfigCommon.SETTING_DATA_MYSQL_DATABASE = this.getConfiguration().getString("Setting.Data.Mysql.Database");
        ConfigCommon.SETTING_DATA_MYSQL_USERNAME = this.getConfiguration().getString("Setting.Data.Mysql.Username");
        ConfigCommon.SETTING_DATA_MYSQL_PASSWORD = this.getConfiguration().getString("Setting.Data.Mysql.Password");

        ConfigCommon.SETTING_COMMAND_MAIN = this.getConfiguration().getString("Setting.Command.Main");
        ConfigCommon.SETTING_COMMAND_CHILD = this.getConfiguration().getString("Setting.Command.Child");

        ConfigCommon.OVERALL_SITUATION_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Enable");
        ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Variable.Enable");
        ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT = this.getConfiguration().getString("OverallSituation.Variable.DateFormat");
        ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX = this.getConfiguration().getString("OverallSituation.Variable.Prefix");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Message.Enable");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_RELOAD = this.getConfiguration().getString("OverallSituation.Message.Reload");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_COMPLETE = this.getConfiguration().getString("OverallSituation.Message.Complete");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION = this.getConfiguration().getString("OverallSituation.Message.MissingPermission");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_NEED_PLAYER = this.getConfiguration().getString("OverallSituation.Message.NeedPlayer");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_MODULE_NOT_FOUND = this.getConfiguration().getString("OverallSituation.Message.ModuleNotFound");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_MODULE_OPENED = this.getConfiguration().getString("OverallSituation.Message.ModuleOpened");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_MODULE_CLOSED = this.getConfiguration().getString("OverallSituation.Message.ModuleClosed");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_MODULE_OPEN = this.getConfiguration().getString("OverallSituation.Message.ModuleOpen");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_MODULE_CLOSE = this.getConfiguration().getString("OverallSituation.Message.ModuleClose");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_MODULE_INFO = this.getConfiguration().getString("OverallSituation.Message.ModuleInfo");
        ConfigCommon.OVERALL_SITUATION_MESSAGE_HELP = this.getConfiguration().getString("OverallSituation.Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}