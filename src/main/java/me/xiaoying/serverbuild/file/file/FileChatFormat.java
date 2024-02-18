package me.xiaoying.serverbuild.file.file;

import me.xiaoying.serverbuild.constant.ConstantChatFormat;
import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * File ChatFormat
 */
public class FileChatFormat implements SubFile {
    ConstantChatFormat constant = new ConstantChatFormat();
    File file = new File(ServerUtil.getDataFolder(), "ChatFormat.yml");
    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public String getPath() {
        return this.file.getPath();
    }

    @Override
    public File getDataFolder() {
        return ServerUtil.getDataFolder();
    }

    @Override
    public YamlConfiguration getYamlConfiguration() {
        return this.configuration;
    }

    @Override
    public void file() {
        if (!this.file.exists()) ServerUtil.saveResources("ChatFormat.yml");
    }

    @Override
    public void initialize() {

    }

    @Override
    public void delete() {
        this.file.delete();
    }

    @Override
    public ConstantCommon getConstant() {
        return this.constant;
    }
}