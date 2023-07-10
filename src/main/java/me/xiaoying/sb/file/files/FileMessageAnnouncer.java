package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.MessageAnnouncerConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileMessageAnnouncer extends SubFile {
    public static YamlConfiguration messageAnnouncer;
    File messageAnnouncerFile = new File(ServerUtil.getDataFolder(), "MessageAnnouncer.yml");

    @Override
    public void newFile() {
        if (!messageAnnouncerFile.exists()) ServerUtil.saveResources("MessageAnnouncer.yml");
        messageAnnouncer = YamlConfiguration.loadConfiguration(messageAnnouncerFile);
    }

    @Override
    public void delFile() {
        messageAnnouncerFile.delete();
    }

    @Override
    public void initFile() {
        MessageAnnouncerConstant.SET_ENABLE = messageAnnouncer.getBoolean("Enable");

        MessageAnnouncerConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : messageAnnouncer.getString("Set.DateFormat");
        MessageAnnouncerConstant.MESSAGE_HELP = messageAnnouncer.getStringList("Use-Help");

        MessageAnnouncerConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : messageAnnouncer.getString("Message.Prefix");
        MessageAnnouncerConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : messageAnnouncer.getString("Message.Reload");
        MessageAnnouncerConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : messageAnnouncer.getString("Message.NoPermission");

        MessageAnnouncerConstant.ENABLE_CHAT_ANNOUNCER = messageAnnouncer.getBoolean("MessageAnnouncer.Chat.Enable");
        MessageAnnouncerConstant.ENABLE_TITLE_ANNOUNCER = messageAnnouncer.getBoolean("MessageAnnouncer.Title.Enable");
        MessageAnnouncerConstant.ENABLE_ACTIONBAR_ANNOUNCER = messageAnnouncer.getBoolean("MessageAnnouncer.ActionBar.Enable");

        MessageAnnouncerConstant.DELAY_CHAT_ANNOUNCER = messageAnnouncer.getLong("MessageAnnouncer.Chat.Delay");
        MessageAnnouncerConstant.DELAY_TITLE_ANNOUNCER = messageAnnouncer.getLong("MessageAnnouncer.Title.Delay");
        MessageAnnouncerConstant.DELAY_ACTIONBAR_ANNOUNCER = messageAnnouncer.getLong("MessageAnnouncer.ActionBar.Delay");
    }
}