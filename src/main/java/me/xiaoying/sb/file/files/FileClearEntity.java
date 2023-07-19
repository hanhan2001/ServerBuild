package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ClearEntityConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileClearEntity extends SubFile {
    public static YamlConfiguration clearEntity;
    File clearEntityFile = new File(ServerUtil.getDataFolder(), "ClearEntity.yml");

    @Override
    public void newFile() {
        if (!this.clearEntityFile.exists()) ServerUtil.saveResources("ClearEntity.yml");
        clearEntity = YamlConfiguration.loadConfiguration(this.clearEntityFile);
    }

    @Override
    public void delFile() {
        this.clearEntityFile.delete();
    }

    @Override
    public void initFile() {
        ClearEntityConstant.SET_ENABLE = clearEntity.getBoolean("Enable");

        ClearEntityConstant.CLEAR_PET = clearEntity.getBoolean("ClearEntity.ClearPet");
        ClearEntityConstant.CLEAR_NAMED = clearEntity.getBoolean("ClearEntity.ClearNamed");
        ClearEntityConstant.CLEAR_TOTAL = clearEntity.getBoolean("ClearEntity.Total.Enable");

        ClearEntityConstant.CLEAR_SECOND = clearEntity.getInt("ClearEntity.SecondTime");
        ClearEntityConstant.CLEAR_TOTAL_LIMIT = clearEntity.getInt("ClearEntity.Total.Limit");

        ClearEntityConstant.CLEAR_ENTITY = clearEntity.getStringList("ClearEntity.Entity");
        ClearEntityConstant.CLEAR_NOCLEAR = clearEntity.getStringList("ClearEntity.NoClear");
        ClearEntityConstant.CLEAR_TIME = clearEntity.getStringList("ClearEntity.CountTime");
        ClearEntityConstant.CLEAR_MESSAGE_COUNTDOWN = YamlUtil.getStringList(clearEntity, "ClearMessage.CountDown");
    }
}