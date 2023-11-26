package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ConstantCommon;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * FileService file methods
 */
public interface SubFile {
    String getName();
    String getPath();
    File getDataFolder();
    YamlConfiguration getYamlConfiguration();

    void file();
    void initialize();
    void delete();

    ConstantCommon getConstant();
}