package me.xiaoying.serverbuild.file.resolvelag;

import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.file.FileConfig;
import me.xiaoying.serverbuild.file.SFile;

/**
 * File ResolveLag.yml
 */
public class FileResolveLag extends SFile {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT, SETTING_PREFIX;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_UNKNOWN_WORLD,
            MESSAGE_WORLD_STATE,
            MESSAGE_HELP;

    public FileResolveLag() {
        super("ResolveLag.yml");
    }

    @Override
    public void onLoad() {
        FileResolveLag.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileResolveLag.SETTING_DATEFORMAT = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        FileResolveLag.SETTING_PREFIX = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        FileResolveLag.MESSAGE_RELOAD = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileResolveLag.MESSAGE_MISSING_PERMISSION = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        FileResolveLag.MESSAGE_HELP = this.getConfiguration().getString("Message.Help");

        FileResolveLag.MESSAGE_UNKNOWN_WORLD = this.getConfiguration().getString("Message.UnknownWorld");
        FileResolveLag.MESSAGE_WORLD_STATE = this.getConfiguration().getString("Message.WorldState");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}