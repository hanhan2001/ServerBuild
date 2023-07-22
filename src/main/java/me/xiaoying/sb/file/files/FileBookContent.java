package me.xiaoying.sb.file.files;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.BookContentConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileBookContent extends SubFile {
    public static YamlConfiguration bookContent;
    File file = new File(ServerBuild.getInstance().getDataFolder(), "BookContent.yml");

    @Override
    public void newFile() {
        if (!file.exists()) ServerUtil.saveResources("BookContent.yml");
        bookContent = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        this.file.delete();
    }

    @Override
    public void initFile() {
        BookContentConstant.SET_ENABLE = bookContent.getBoolean("Enable");

        BookContentConstant.MESSAGE_PREFIX = bookContent.getString("Message.Prefix");
        BookContentConstant.MESSAGE_RELOAD = bookContent.getString("Message.Reload");
        BookContentConstant.MESSAGE_NOPERMISSION = bookContent.getString("Message.NoPermission");
        BookContentConstant.MESSAGE_USEPLAYER = bookContent.getString("Message.UsePlayer");
        BookContentConstant.MESSAGE_GIVEBOOK = bookContent.getString("Message.GiveBook");
        BookContentConstant.MESSAGE_NOTFOUNDBOOK = bookContent.getString("Message.NotFoundBook");
        BookContentConstant.SET_VARIABLE_DATEFORMAT = bookContent.getString("Set.DateFormat");

        BookContentConstant.MESSAGE_HELP = bookContent.getStringList("Use-Help");
    }
}