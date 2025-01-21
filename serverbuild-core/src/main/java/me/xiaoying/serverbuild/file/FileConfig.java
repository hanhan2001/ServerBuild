package me.xiaoying.serverbuild.file;

/**
 * File Config.yml
 */
public class FileConfig extends SFile {
    public static boolean SETTING_BSTATS;

    public static String SETTING_DATA_TYPE,
            SETTING_DATA_SQLITE_DATAPATH,
            SETTING_DATA_MYSQL_HOST,
            SETTING_DATA_MYSQL_DATABASE,
            SETTING_DATA_MYSQL_USERNAME,
            SETTING_DATA_MYSQL_PASSWORD;
    public static int SETTING_DATA_MYSQL_PORT;

    public static boolean OVERALL_SITUATION_ENABLE,
            OVERALL_SITUATION_VARIABLE_ENABLE,
            OVERALL_SITUATION_MESSAGE_ENABLE;
    public static String OVERALL_SITUATION_VARIABLE_DATEFORAMT,
            OVERALL_SITUATION_VARIABLE_PREFIX,
            OVERALL_SITUATION_MESSAGE_RELOAD,
            OVERALL_SITUATION_MESSAGE_COMPLETE,
            OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION,
            OVERALL_SITUATION_MESSAGE_NEED_PLAYER,
            OVERALL_SITUATION_MESSAGE_MODULE_NOT_FOUND,
            OVERALL_SITUATION_MESSAGE_MODULE_OPENED,
            OVERALL_SITUATION_MESSAGE_MODULE_CLOSED,
            OVERALL_SITUATION_MESSAGE_MODULE_OPEN,
            OVERALL_SITUATION_MESSAGE_MODULE_CLOSE,
            OVERALL_SITUATION_MESSAGE_MODULE_INFO,
            OVERALL_SITUATION_MESSAGE_HELP;

    public FileConfig() {
        super("Config.yml");
    }

    @Override
    public void onLoad() {
        FileConfig.SETTING_BSTATS = this.getConfiguration().getBoolean("Setting.Bstats");
        FileConfig.SETTING_DATA_TYPE = this.getConfiguration().getString("Setting.Data.Type");
        FileConfig.SETTING_DATA_SQLITE_DATAPATH = this.getConfiguration().getString("Setting.Data.SQLite.DataPath");
        FileConfig.SETTING_DATA_MYSQL_HOST = this.getConfiguration().getString("Setting.Data.Mysql.Host");
        FileConfig.SETTING_DATA_MYSQL_PORT = this.getConfiguration().getInt("Setting.Data.Mysql.Port");
        FileConfig.SETTING_DATA_MYSQL_DATABASE = this.getConfiguration().getString("Setting.Data.Mysql.Database");
        FileConfig.SETTING_DATA_MYSQL_USERNAME = this.getConfiguration().getString("Setting.Data.Mysql.Username");
        FileConfig.SETTING_DATA_MYSQL_PASSWORD = this.getConfiguration().getString("Setting.Data.Mysql.Password");

        FileConfig.OVERALL_SITUATION_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Enable");
        FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Variable.Enable");
        FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT = this.getConfiguration().getString("OverallSituation.Variable.DateFormat");
        FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX = this.getConfiguration().getString("OverallSituation.Variable.Prefix");
        FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Message.Enable");
        FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD = this.getConfiguration().getString("OverallSituation.Message.Reload");
        FileConfig.OVERALL_SITUATION_MESSAGE_COMPLETE = this.getConfiguration().getString("OverallSituation.Message.Complete");
        FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION = this.getConfiguration().getString("OverallSituation.Message.MissingPermission");
        FileConfig.OVERALL_SITUATION_MESSAGE_NEED_PLAYER = this.getConfiguration().getString("OverallSituation.Message.NeedPlayer");
        FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_NOT_FOUND = this.getConfiguration().getString("OverallSituation.Message.ModuleNotFound");
        FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_OPENED = this.getConfiguration().getString("OverallSituation.Message.ModuleOpened");
        FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_CLOSED = this.getConfiguration().getString("OverallSituation.Message.ModuleClosed");
        FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_OPEN = this.getConfiguration().getString("OverallSituation.Message.ModuleOpen");
        FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_CLOSE = this.getConfiguration().getString("OverallSituation.Message.ModuleClose");
        FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_INFO = this.getConfiguration().getString("OverallSituation.Message.ModuleInfo");
        FileConfig.OVERALL_SITUATION_MESSAGE_HELP = this.getConfiguration().getString("OverallSituation.Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}