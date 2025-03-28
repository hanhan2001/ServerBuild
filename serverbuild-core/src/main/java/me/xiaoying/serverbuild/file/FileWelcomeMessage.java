package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.common.ConfigCommon;

public class FileWelcomeMessage extends SFile {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT,
            SETTING_PREFIX;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION;

    public FileWelcomeMessage() {
        super("WelcomeMessage.yml");
    }

    @Override
    public void onLoad() {
        FileWelcomeMessage.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileWelcomeMessage.SETTING_DATEFORMAT = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        FileWelcomeMessage.SETTING_PREFIX = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        FileWelcomeMessage.MESSAGE_RELOAD = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileWelcomeMessage.MESSAGE_MISSING_PERMISSION = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}