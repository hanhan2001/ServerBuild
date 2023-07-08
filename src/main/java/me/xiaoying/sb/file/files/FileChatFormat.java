package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.FileMonitorConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileChatFormat extends SubFile {
    public static YamlConfiguration chatFormat;
    File chatFormatFile = new File(ServerUtil.getDataFolder(), "ChatFormat.yml");

    @Override
    public void newFile() {
        if (!this.chatFormatFile.exists()) ServerUtil.saveResources("ChatFormat.yml");
        chatFormat = YamlConfiguration.loadConfiguration(this.chatFormatFile);
    }

    @Override
    public void delFile() {
        chatFormatFile.delete();
    }

    @Override
    public void initFile() {
        ChatFormatConstant.SET_ENABLE = chatFormat.getBoolean("Enable");
        ChatFormatConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : chatFormat.getString("Set.DateFormat");
        ChatFormatConstant.CHAR_LIMIT_ENABLE = chatFormat.getBoolean("CharacterLimit.Enable");
        ChatFormatConstant.CHAR_LIMIT_LIMIT = chatFormat.getInt("CharacterLimit.Limit");

        ChatFormatConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : chatFormat.getString("Message.Prefix");
        ChatFormatConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_RELOAD : chatFormat.getString("Message.Reload");

        ChatFormatConstant.CHAT_DEFAULTTIME = chatFormat.getInt("Mute.DefaultTime");
        ChatFormatConstant.MESSAGE_MUTEWRONG = chatFormat.getString("Message.MuteWrong");
        ChatFormatConstant.CHAT_MUTE_MESSAGE = YamlUtil.getStringList(chatFormat, "Mute.Message");
        ChatFormatConstant.MUTE_SUCCESS = chatFormat.getString("Message.MuteSuccess");

        ChatFormatConstant.CHAR_LIMIT_MESSAGE = YamlUtil.getStringList(chatFormat, "CharacterLimit.Message");
        ChatFormatConstant.CALL_ENABLE = chatFormat.getBoolean("Call.Enable");
        ChatFormatConstant.CALL_KEY = chatFormat.getString("Call.Key");
        ChatFormatConstant.CALL_SOUND = chatFormat.getString("Call.Sound");
        ChatFormatConstant.MESSAGE_NOTFOUNPAYER = chatFormat.getString("Message.NotFoundPlayer");

        ChatFormatConstant.BLACK_TERMS_ENABLE = chatFormat.getBoolean("BlackTerms.Enable");
        ChatFormatConstant.BLACK_TERMS_EVERY = chatFormat.getBoolean("BlackTerms.ForEveryBody");
        ChatFormatConstant.BLACK_TERMS_CANCEL = chatFormat.getBoolean("BlackTerms.Cancel");
        ChatFormatConstant.BLACK_TERMS_TODO = chatFormat.getStringList("BlackTerms.Todo");
        ChatFormatConstant.BLACK_TERMS_MESSAGE = YamlUtil.getStringList(chatFormat, "BlackTerms.Message");
        ChatFormatConstant.BLACK_TERMS_TERMS = chatFormat.getStringList("BlackTerms.Terms");

        ChatFormatConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_NOPERMISSION : chatFormat.getString("Message.NoPermission");

        ChatFormatConstant.MESSAGE_HELP = chatFormat.getStringList("Use-Help");
    }
}
