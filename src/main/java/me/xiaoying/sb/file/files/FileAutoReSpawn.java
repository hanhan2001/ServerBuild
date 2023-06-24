package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.AutoReSpawnConstant;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileAutoReSpawn extends SubFile {
    public static YamlConfiguration autoReSpawn;
    File file = new File(ServerUtil.getDataFolder(), "AutoReSpawn.yml");

    @Override
    public void newFile() {
        if (!this.file.exists()) ServerUtil.saveResources("AutoReSpawn.yml");
        autoReSpawn = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        this.file.delete();
    }

    @Override
    public void initFile() {
        AutoReSpawnConstant.SET_ENABLE = autoReSpawn.getBoolean("Enable");
        AutoReSpawnConstant.ENABLE_CHAT = autoReSpawn.getBoolean("AutoReSpawn.Chat.Enable");
        AutoReSpawnConstant.ENABLE_TITLE = autoReSpawn.getBoolean("AutoReSpawn.Title.Enable");
        AutoReSpawnConstant.ENABLE_ACTIONBAR = autoReSpawn.getBoolean("AutoReSpawn.ActionBar.Enable");

        AutoReSpawnConstant.CHECK_TIME = autoReSpawn.getInt("AutoReSpawn.CheckTime");
        AutoReSpawnConstant.TITLE_FADE_IN = autoReSpawn.getInt("AutoReSpawn.Title.FadeIn");
        AutoReSpawnConstant.TITLE_STAY = autoReSpawn.getInt("AutoReSpawn.Title.Stay");
        AutoReSpawnConstant.TITLE_FADEOUT = autoReSpawn.getInt("AutoReSpawn.Title.FadeOut");
        AutoReSpawnConstant.ACTIONBAR_TIME = autoReSpawn.getInt("AutoReSpawn.ActionBar.Time");

        AutoReSpawnConstant.TITLE_TITLE = autoReSpawn.getString("AutoReSpawn.Title.Title");
        AutoReSpawnConstant.TITLE_SUBTITLE = autoReSpawn.getString("AutoReSpawn.Title.SubTitle");
        AutoReSpawnConstant.ACTIONBAR_MESSAGE = autoReSpawn.getString("AutoReSpawn.ActionBar.Message");
        AutoReSpawnConstant.MESSAGE_PREFIX = autoReSpawn.getString("Message.Prefix");
        AutoReSpawnConstant.MESSAGE_RELOAD = autoReSpawn.getString("Message.Reload");
        AutoReSpawnConstant.MESSAGE_NOPERMISSION = autoReSpawn.getString("Message.NoPermission");

        AutoReSpawnConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : autoReSpawn.getString("Set.DateFormat");

        AutoReSpawnConstant.CHAT_FORMAT = autoReSpawn.getStringList("AutoReSpawn.Chat.Format");
        AutoReSpawnConstant.MESSAGE_HELP = autoReSpawn.getStringList("Use-Help");
    }
}