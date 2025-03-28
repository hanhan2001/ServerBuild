package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.common.ConfigCommon;

/**
 * File MessageAnnouncer.yml
 */
public class FileMessageAnnouncer extends SFile {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT,
            SETTING_PREFIX;

    public static int MESSAGE_ANNOUNCER_DELAY;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION;

    public FileMessageAnnouncer() {
        super("MessageAnnouncer.yml");
    }

    @Override
    public void onLoad() {
        FileMessageAnnouncer.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileMessageAnnouncer.SETTING_PREFIX = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");
        FileMessageAnnouncer.SETTING_DATEFORMAT = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");

        FileMessageAnnouncer.MESSAGE_ANNOUNCER_DELAY = this.getConfiguration().getInt("MessageAnnouncer.Delay");

        FileMessageAnnouncer.MESSAGE_RELOAD = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileMessageAnnouncer.MESSAGE_MISSING_PERMISSION = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}