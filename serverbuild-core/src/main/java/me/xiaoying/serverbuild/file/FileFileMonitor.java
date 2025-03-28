package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.common.ConfigCommon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileFileMonitor extends SFile {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT, SETTING_PREFIX;

    public static int FILE_MONITOR_INTERVAL_TIME;
    public static List<String> FILE_MONITOR_EVENT;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION;

    public FileFileMonitor() {
        super("FileMonitor.yml");
    }

    @Override
    public void onLoad() {
        FileFileMonitor.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileFileMonitor.SETTING_PREFIX = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");
        FileFileMonitor.SETTING_DATEFORMAT = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");

        FileFileMonitor.FILE_MONITOR_INTERVAL_TIME = this.getConfiguration().getInt("FileMonitor.IntervalTime");
        FileFileMonitor.FILE_MONITOR_EVENT = new ArrayList<>(Arrays.asList(this.getConfiguration().getString("FileMonitor.Event").split("\n")));

        FileFileMonitor.MESSAGE_RELOAD = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileFileMonitor.MESSAGE_MISSING_PERMISSION = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}