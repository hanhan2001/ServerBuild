package me.xiaoying.serverbuild.file;

public class FileWelcomeMessage extends SFile {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT,
            SETTING_PREFIX;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_HELP;

    public FileWelcomeMessage() {
        super("WelcomeMessage.yml");
    }

    @Override
    public void onLoad() {
        FileWelcomeMessage.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileWelcomeMessage.SETTING_DATEFORMAT = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        FileWelcomeMessage.SETTING_PREFIX = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        FileWelcomeMessage.MESSAGE_RELOAD = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileWelcomeMessage.MESSAGE_MISSING_PERMISSION = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        FileWelcomeMessage.MESSAGE_HELP = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}