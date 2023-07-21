package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.constant.ClearEntityConstant;
import me.xiaoying.sb.constant.ConfigConstant;
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
        ClearEntityConstant.CLEAR_NOTCLEAR_ITEM = clearEntity.getStringList("ClearEntity.NotClearItem");
        ClearEntityConstant.CLEAR_NOTCLEAR_MONSTER = clearEntity.getStringList("ClearEntity.NotClearMonster");
        ClearEntityConstant.CLEAR_NOTCLEAR_ANIMAL = clearEntity.getStringList("ClearEntity.NotClearAnimal");
        ClearEntityConstant.CLEAR_TIME = clearEntity.getStringList("ClearEntity.CountTime");
        ClearEntityConstant.CLEAR_BLACKWORLDS = clearEntity.getStringList("ClearEntity.BlackWorlds");
        ClearEntityConstant.CLEAR_MESSAGE_COUNTDOWN = YamlUtil.getStringList(clearEntity, "ClearMessage.CountDown");

        ClearEntityConstant.MESSAGE_HELP = clearEntity.getStringList("Use-Help");
        ClearEntityConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : clearEntity.getString("Set.DateFormat");
        ClearEntityConstant.MESSAGE_PREFIX = clearEntity.getString("Message.Prefix");
        ClearEntityConstant.MESSAGE_RELOAD = clearEntity.getString("Message.Reload");
        ClearEntityConstant.MESSAGE_NOPERMISSION = clearEntity.getString("Message.NoPermission");
    }
}