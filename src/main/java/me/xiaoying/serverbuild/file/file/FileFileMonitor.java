package me.xiaoying.serverbuild.file.file;

import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.constant.ConstantFileMonitor;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * File FileMonitor
 */
public class FileFileMonitor implements SubFile {
    private final ConstantFileMonitor constant = new ConstantFileMonitor();
    private final File file = new File(ServerUtil.getDataFolder(), "FileMonitor.yml");
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
        if (!this.file.exists()) ServerUtil.saveResources("FileMonitor.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void initialize() {
        this.getConstant().ENABLE_FILEMONITOR = configuration.getBoolean("Enable");

        this.getConstant().SET_DATEFORMAT = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_VARIABLE ? ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT : this.configuration.getString("Set.DateFormat");
        this.getConstant().SET_PREFIX = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX : this.configuration.getString("Set.Prefix");
        this.getConstant().MESSAGE_RELOAD = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_MESSAGE_RELOAD : this.configuration.getString("Message.Reload");
        this.getConstant().MESSAGE_NOPERMISSION = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_MESSAGE_NOPERMISSION : this.configuration.getString("Message.NoPermission");
        this.getConstant().MESSAGE_COMPLETE = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_MESSAGE_COMPLETE : this.configuration.getString("Message.Complete");

        // 获取 AutoReSpawn Event
        if (!StringUtil.isEmpty(this.configuration.getString("FileMonitor.Event")))
            this.getConstant().FILEMONITOR_EVENT = Arrays.asList(configuration.getString("FileMonitor.Event").split("\n"));
        else
            this.getConstant().FILEMONITOR_EVENT = new ArrayList<>();

        this.getConstant().MESSAGE_HELP = ConstantCommon.SYSTEM_ENABLE_OVERALL && ConstantCommon.SYSTEM_ENABLE_OVERALL_MESSAGE ? ConstantCommon.SYSTEM_OVERALL_MESSAGE_HELP : this.configuration.getStringList("Message.Help");
    }

    @Override
    public void delete() {
        this.file.delete();
    }

    @Override
    public ConstantFileMonitor getConstant() {
        return this.constant;
    }
}