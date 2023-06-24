package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.WelcomeMessageConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileWelcomeMessage extends SubFile {
    public static YamlConfiguration welcomeMessage;
    File welcomeMessageFile = new File(ServerUtil.getDataFolder(), "WelcomeMessage.yml");

    @Override
    public void newFile() {
        if (!this.welcomeMessageFile.exists()) ServerUtil.saveResources("WelcomeMessage.yml");
        welcomeMessage = YamlConfiguration.loadConfiguration(this.welcomeMessageFile);
    }

    @Override
    public void delFile() {
        welcomeMessageFile.delete();
    }

    @Override
    public void initFile() {
        WelcomeMessageConstant.SET_ENABLE = welcomeMessage.getBoolean("Enable");
        WelcomeMessageConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : welcomeMessage.getString("Message.Prefix");
        WelcomeMessageConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_RELOAD : welcomeMessage.getString("Message.Reload");
        WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : welcomeMessage.getString("Set.DateFormat");
        WelcomeMessageConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_NOPERMISSION : welcomeMessage.getString("Message.NoPermission");

        WelcomeMessageConstant.MESSAGE_HELP = welcomeMessage.getStringList("Use-Help");
    }
}
