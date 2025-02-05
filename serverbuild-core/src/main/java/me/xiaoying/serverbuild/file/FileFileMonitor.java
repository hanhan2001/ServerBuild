package me.xiaoying.serverbuild.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileFileMonitor extends SFile {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT, SETTING_PREFIX;

    public static int FILE_MONITOR_INTERVAL_TIME;
    public static List<String> FILE_MONITOR_EVENT;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_HELP;

    public FileFileMonitor() {
        super("FileMonitor.yml");
    }

    @Override
    public void onLoad() {
        FileFileMonitor.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileFileMonitor.SETTING_PREFIX = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");
        FileFileMonitor.SETTING_DATEFORMAT = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");

        FileFileMonitor.FILE_MONITOR_INTERVAL_TIME = this.getConfiguration().getInt("FileMonitor.IntervalTime");
        FileFileMonitor.FILE_MONITOR_EVENT = new ArrayList<>(Arrays.asList(this.getConfiguration().getString("FileMonitor.Event").split("\n")));

        FileFileMonitor.MESSAGE_RELOAD = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileFileMonitor.MESSAGE_MISSING_PERMISSION = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        FileFileMonitor.MESSAGE_HELP = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}