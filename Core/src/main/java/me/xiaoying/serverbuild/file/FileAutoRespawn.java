package me.xiaoying.serverbuild.file;

public class FileAutoRespawn extends File {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT,
            SETTING_PREFIX;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_NOT_FOUND_PLAYER,
            MESSAGE_COMPLETE,
            MESSAGE_HELP;

    public static String AUTO_RESPAWN_TYPE;

    public static long AUTO_RESPAWN_SERVER,
            AUTO_RESPAWN_PLAYER;

    public static boolean AUTO_RESPAWN_JOIN_RESPAWN,
            AUTO_RESPAWN_QUIT_RESPAWN;

    public FileAutoRespawn() {
        this("AutoRespawn.yml");
    }

    public FileAutoRespawn(String file) {
        super(file);
    }

    @Override
    public void onLoad() {
        FileAutoRespawn.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileAutoRespawn.SETTING_PREFIX = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");
        FileAutoRespawn.SETTING_DATEFORMAT = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.DateFormat");

        FileAutoRespawn.MESSAGE_RELOAD = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileAutoRespawn.MESSAGE_MISSING_PERMISSION = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.MissingPermission");
        FileAutoRespawn.MESSAGE_NOT_FOUND_PLAYER = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.NotFoundPlayer");
        FileAutoRespawn.MESSAGE_COMPLETE = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_COMPLETE : this.getConfiguration().getString("Message.Complete");
        FileAutoRespawn.MESSAGE_HELP = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");

        FileAutoRespawn.AUTO_RESPAWN_TYPE = this.getConfiguration().getString("AutoRespawn.Type");

        FileAutoRespawn.AUTO_RESPAWN_SERVER = this.getConfiguration().getLong("AutoRespawn.Server");
        FileAutoRespawn.AUTO_RESPAWN_PLAYER = this.getConfiguration().getLong("AutoRespawn.Player");

        FileAutoRespawn.AUTO_RESPAWN_JOIN_RESPAWN = this.getConfiguration().getBoolean("AutoRespawn.JoinRespawn");
        FileAutoRespawn.AUTO_RESPAWN_QUIT_RESPAWN = this.getConfiguration().getBoolean("AutoRespawn.QuitRespawn");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}