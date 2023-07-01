package me.xiaoying.sb.files.config;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTeleport {
    public static YamlConfiguration teleport;

    public static int SET_OVERDUE;
    public static boolean SET_ENABLE;
    public static String SET_VARIABLE_DATEFORMAT;

    public static String MESSAGE_PREFIX,
            MESSAGE_RELOAD,
            MESSAGE_NOPERMISSION,
            MESSAGE_USEPLAYER, MESSAGE_NOTFOUNDPLAYER;

    public static List<String> MESSAGE_HELP;

    public static List<String> TP_TRIGGER,
            TP_TRIGGERED,
            TPPOS_TRIGGER,
            TPA_TRIGGER,
            TPA_TRIGGERED,
            TPA_MESSAGE,
            TPHERE_TRIGGER,
            TPHERE_TRIGGERED,
            TPHERE_MESSAGE,
            TPDENY_TRIGGER,
            TPDENY_TRIGGERED,
            TPDENY_OVERTIME,
            TPDENY_NOAPPLY,
            TPACCEPT_TRIGGER,
            TPACCEPT_TRIGGERED,
            TPACCEPT_OVERTIME,
            TPACCEPT_NOAPPLY,
            TPCANCLE_TRIGGER,
            TPCANCLE_TRIGGERED,
            TPCANCLE_OVERTIME,
            TPCANCLE_NOAPPLY,
            BACK_TRIGGER,
            BACK_NOTFOUNDPOINT;

    public static void fileTeleport() {
        File teleportFile = new File(ServerUtil.getDataFolder(), "Teleport.yml");
        if (!teleportFile.exists()) ServerUtil.saveResources("Teleport.yml");
        teleport = YamlConfiguration.loadConfiguration(teleportFile);

        readConfig();
    }

    private static void readConfig() {
        TP_TRIGGER = getStringList("Tp.Format.Trigger");
        TP_TRIGGERED = getStringList("Tp.Format.Triggered");
        TPPOS_TRIGGER = getStringList("Tppos.Format.Trigger");
        TPA_TRIGGER = getStringList("Tpa.Format.Trigger");
        TPA_TRIGGERED = getStringList("Tpa.Format.Triggered");
        TPA_MESSAGE = getStringList("Tpa.Message.AlreadyApply");
        TPHERE_TRIGGER = getStringList("Tphere.Format.Trigger");
        TPHERE_TRIGGERED = getStringList("Tphere.Format.Triggered");
        TPHERE_MESSAGE = getStringList("Tphere.Message.AlreadyApply");
        TPDENY_TRIGGER = getStringList("Tpdeny.Format.Trigger");
        TPDENY_TRIGGERED = getStringList("Tpdeny.Format.Triggered");
        TPDENY_OVERTIME = getStringList("Tpdeny.Message.OverTime");
        TPDENY_NOAPPLY = getStringList("Tpdeny.Message.NoApply");
        TPACCEPT_TRIGGER = getStringList("Tpaccept.Format.Trigger");
        TPACCEPT_TRIGGERED = getStringList("Tpaccept.Format.Triggered");
        TPACCEPT_OVERTIME = getStringList("Tpaccept.Message.OverTime");
        TPACCEPT_NOAPPLY = getStringList("Tpaccept.Message.NoApply");
        TPCANCLE_TRIGGER = getStringList("Tpacancle.Format.Trigger");
        TPCANCLE_TRIGGERED = getStringList("Tpacancle.Format.Triggered");
        TPCANCLE_OVERTIME = getStringList("Tpacancle.Message.OverTime");
        TPCANCLE_NOAPPLY = getStringList("Tpacancle.Message.NoApply");
        BACK_TRIGGER = getStringList("Back.Format.Trigger");
        BACK_NOTFOUNDPOINT = getStringList("Back.Message.NotFoundPoint");

        SET_ENABLE = teleport.getBoolean("Enable");
        SET_OVERDUE = teleport.getInt("Set.Overdue");
        MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : teleport.getString("Message.Prefix");
        MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_RELOAD : teleport.getString("Message.Reload");
        SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : teleport.getString("Set.DateFormat");
        MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_NOPERMISSION : teleport.getString("Message.NoPermission");
        MESSAGE_HELP = teleport.getStringList("Use-Help");
        MESSAGE_NOTFOUNDPLAYER = teleport.getString("Message.NotFoundPlayer");
        MESSAGE_NOTFOUNDPLAYER = teleport.getString("Message.NotPlayerApply");
    }

    private static List<String> getStringList(String path) {
        List<String> function;
        try {
            function = teleport.getStringList(path);
        } catch (Exception e) {
            function = new ArrayList<>();
            function.add(teleport.getString(path));
        }
        return function;
    }
}