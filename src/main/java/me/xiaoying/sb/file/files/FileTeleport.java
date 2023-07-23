package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.TeleportConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileTeleport extends SubFile {
    public static YamlConfiguration teleport;
    File file = new File(ServerUtil.getDataFolder(), "Teleport.yml");

    @Override
    public void newFile() {
        if (!this.file.exists()) ServerUtil.saveResources("Teleport.yml");
        teleport = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        file.delete();
    }

    @Override
    public void initFile() {
        TeleportConstant.TP_PLAYER_TRIGGER = YamlUtil.getStringList(teleport, "Tp.Player.Format.Trigger");
        TeleportConstant.TP_PLAYER_TRIGGERED = YamlUtil.getStringList(teleport, "Tp.Player.Format.Triggered");
        TeleportConstant.TP_POS_TRIGGER = YamlUtil.getStringList(teleport, "Tp.Position.Format.Trigger");
        TeleportConstant.TPA_TRIGGER = YamlUtil.getStringList(teleport, "Tpa.Format.Trigger");
        TeleportConstant.TPA_TRIGGERED = YamlUtil.getStringList(teleport, "Tpa.Format.Triggered");
        TeleportConstant.TPA_MESSAGE = YamlUtil.getStringList(teleport, "Tpa.Message.AlreadyApply");
        TeleportConstant.TPHERE_TRIGGER = YamlUtil.getStringList(teleport, "Tphere.Format.Trigger");
        TeleportConstant.TPHERE_TRIGGERED = YamlUtil.getStringList(teleport, "Tphere.Format.Triggered");
        TeleportConstant.TPHERE_MESSAGE = YamlUtil.getStringList(teleport, "Tphere.Message.AlreadyApply");
        TeleportConstant.TPDENY_TRIGGER = YamlUtil.getStringList(teleport, "Tpdeny.Format.Trigger");
        TeleportConstant.TPDENY_TRIGGERED = YamlUtil.getStringList(teleport, "Tpdeny.Format.Triggered");
        TeleportConstant.TPDENY_OVERTIME = YamlUtil.getStringList(teleport, "Tpdeny.Message.OverTime");
        TeleportConstant.TPDENY_NOAPPLY = YamlUtil.getStringList(teleport, "Tpdeny.Message.NoApply");
        TeleportConstant.TPACCEPT_TRIGGER = YamlUtil.getStringList(teleport, "Tpaccept.Format.Trigger");
        TeleportConstant.TPACCEPT_TRIGGERED = YamlUtil.getStringList(teleport, "Tpaccept.Format.Triggered");
        TeleportConstant.TPACCEPT_OVERTIME = YamlUtil.getStringList(teleport, "Tpaccept.Message.OverTime");
        TeleportConstant.TPACCEPT_NOAPPLY = YamlUtil.getStringList(teleport, "Tpaccept.Message.NoApply");
        TeleportConstant.TPCANCLE_TRIGGER = YamlUtil.getStringList(teleport, "Tpacancle.Format.Trigger");
        TeleportConstant.TPCANCLE_TRIGGERED = YamlUtil.getStringList(teleport, "Tpacancle.Format.Triggered");
        TeleportConstant.TPCANCLE_OVERTIME = YamlUtil.getStringList(teleport, "Tpacancle.Message.OverTime");
        TeleportConstant.TPCANCLE_NOAPPLY = YamlUtil.getStringList(teleport, "Tpacancle.Message.NoApply");
        TeleportConstant.BACK_TRIGGER = YamlUtil.getStringList(teleport, "Back.Format.Trigger");
        TeleportConstant.BACK_NOTFOUNDPOINT = YamlUtil.getStringList(teleport, "Back.Message.NotFoundPoint");

        TeleportConstant.SET_ENABLE = teleport.getBoolean("Enable");
        TeleportConstant.SET_OVERDUE = teleport.getInt("Set.Overdue");
        TeleportConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : teleport.getString("Message.Prefix");
        TeleportConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_RELOAD : teleport.getString("Message.Reload");
        TeleportConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : teleport.getString("Set.DateFormat");
        TeleportConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_NOPERMISSION : teleport.getString("Message.NoPermission");
        TeleportConstant.MESSAGE_HELP = teleport.getStringList("Use-Help");
        TeleportConstant.MESSAGE_NOTFOUNDPLAYER = teleport.getString("Message.NotFoundPlayer");
        TeleportConstant.MESSAGE_NOTFOUNDPLAYER = teleport.getString("Message.NotPlayerApply");
    }
}
