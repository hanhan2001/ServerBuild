package me.xiaoying.serverbuild.file.file;

import me.xiaoying.serverbuild.constant.ConstantAutoReSpawn;
import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * File AutoReSpawn
 */
public class FileAutoReSpawn implements SubFile {
    private final ConstantAutoReSpawn constant = new ConstantAutoReSpawn();
    private final File file = new File(ServerUtil.getDataFolder(), "AutoReSpawn.yml");
    private YamlConfiguration configuration;

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public String getPath() {
        return this.file.getAbsolutePath();
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
        if (!this.file.exists()) ServerUtil.saveResources("AutoReSpawn.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void initialize() {
        this.getConstant().ENABLE_AUTORESPAWN = configuration.getBoolean("Enable");

        this.getConstant().SET_DATEFORMAT = configuration.getString("Set.DateFormat");
        this.getConstant().AUTORESPAWN_TYPE = configuration.getString("AutoReSpawn.Type");
        // 过滤 AutoReSpawn type，避免出现问题
        if (StringUtil.isEmpty(this.getConstant().AUTORESPAWN_TYPE)) {
            this.getConstant().AUTORESPAWN_TYPE = "server";
            ServerUtil.sendMessage("&e指定 AutoReSpawn 检测类型为空，将自动定义为 server!", true);
        }
        if (!this.getConstant().AUTORESPAWN_TYPE.equalsIgnoreCase("server") && !this.getConstant().AUTORESPAWN_TYPE.equalsIgnoreCase("player")) {
            this.getConstant().AUTORESPAWN_TYPE = "server";
            ServerUtil.sendMessage("&e指定 AutoReSpawn 检测类型不存在(可选 player/server)，将自动定义为 server!", true);
        }
        this.getConstant().AUTORESPAWN_TICK = this.configuration.getInt("AutoReSpawn.Tick");

        this.getConstant().SET_DATEFORMAT = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_VARIABLE ? ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT : this.configuration.getString("Set.DateFormat");
        this.getConstant().SET_PREFIX = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX : this.configuration.getString("Set.Prefix");
        this.getConstant().MESSAGE_RELOAD = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_MESSAGE_RELOAD : this.configuration.getString("Message.Reload");
        this.getConstant().MESSAGE_NOPERMISSION = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_MESSAGE_NOPERMISSION : this.configuration.getString("Message.NoPermission");

        // 获取 AutoReSpawn Event
        if (!StringUtil.isEmpty(this.configuration.getString("AutoReSpawn.Event")))
            this.getConstant().AUTORESPAWN_EVENT = Arrays.asList(configuration.getString("AutoReSpawn.Event").split("\n"));
        else
            this.getConstant().AUTORESPAWN_EVENT = new ArrayList<>();

        this.getConstant().MESSAGE_HELP = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_MESSAGE_HELP : this.configuration.getStringList("Message.Help");
    }

    @Override
    public void delete() {
        this.file.delete();
    }

    @Override
    public ConstantAutoReSpawn getConstant() {
        return this.constant;
    }
}