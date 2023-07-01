package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.FileMonitorConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFileMonitor extends SubFile {
    public static YamlConfiguration fileMonitor;
    File fileMonitorFile = new File(ServerUtil.getDataFolder(), "FileMonitor.yml");

    @Override
    public void newFile() {
        if (!this.fileMonitorFile.exists()) ServerUtil.saveResources("FileMonitor.yml");
        fileMonitor = YamlConfiguration.loadConfiguration(this.fileMonitorFile);
    }

    @Override
    public void delFile() {
        this.fileMonitorFile.delete();
    }

    @Override
    public void initFile() {
        FileMonitorConstant.SET_ENABLE = fileMonitor.getBoolean("Enable");
        FileMonitorConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_VARIABLE ? ConfigConstant.OVERALL_VARIABLE_DATAFORMAT : fileMonitor.getString("Set.DateFormat");
        FileMonitorConstant.SET_MESSAGE_CONSOLE = fileMonitor.getBoolean("FileMonitor.Message.Console");
        FileMonitorConstant.SET_MESSAGE_OPERATOR = fileMonitor.getBoolean("FileMonitor.Message.Operator.Enable");
        FileMonitorConstant.SET_MESSAGE_OPERATOR_CHAT = fileMonitor.getBoolean("FileMonitor.Message.Operator.Set.Chat.Enable");
        FileMonitorConstant.SET_MESSAGE_OPERATOR_TITLE = fileMonitor.getBoolean("FileMonitor.Message.Operator.Set.Title.Enable");
        FileMonitorConstant.SET_MESSAGE_OPERATOR_ACTIONBAR = fileMonitor.getBoolean("FileMonitor.Message.Operator.Set.ActionBar.Enable");

        FileMonitorConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : fileMonitor.getString("Message.Prefix");
        FileMonitorConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_RELOAD : fileMonitor.getString("Message.Reload");
        FileMonitorConstant.MESSAGE_UPDATE = fileMonitor.getString("Message.Update");
        FileMonitorConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_NOPERMISSION : fileMonitor.getString("Message.NoPermission");

        FileMonitorConstant.MESSAGE_OPERATOR_CHAT = getStringList("FileMonitor.Message.Operator.Set.Chat.Message");
        FileMonitorConstant.MESSAGE_OPERATOR_TITLE_TITLE = fileMonitor.getString("FileMonitor.Message.Operator.Set.Title.Title");
        FileMonitorConstant.MESSAGE_OPERATOR_TITLE_SUBTITLE = fileMonitor.getString("FileMonitor.Message.Operator.Set.Title.SubTitle");
        FileMonitorConstant.MESSAGE_OPERATOR_ACTIONBAR = fileMonitor.getString("FileMonitor.Message.Operator.Set.ActionBar.Message");
    }

    private static List<String> getStringList(String path) {
        List<String> function;
        try {
            function = fileMonitor.getStringList(path);
        } catch (Exception e) {
            function = new ArrayList<>();
            function.add(fileMonitor.getString(path));
        }
        return function;
    }
}