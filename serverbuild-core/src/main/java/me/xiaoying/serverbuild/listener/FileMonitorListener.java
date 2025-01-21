package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileFileMonitor;
import me.xiaoying.serverbuild.file.SFile;
import me.xiaoying.serverbuild.inf.FileWatcherInterface;
import org.bukkit.Bukkit;

import java.io.File;

public class FileMonitorListener implements FileWatcherInterface {
    @Override
    public void onCreate(File file) {

    }

    @Override
    public void onDelete(File file) {

    }

    @Override
    public void onChange(File file) {
        // 特殊文件
        if (file.getName().equalsIgnoreCase("Config.yml")) {
            SBPlugin.getFileManager().getFile("Config.yml").load();
            return;
        }

        SBPlugin.getModuleManager().getModules().forEach(module -> {
            for (SFile moduleSFile : module.getFiles()) {
                if (!moduleSFile.getName().equalsIgnoreCase(file.getName()))
                    return;

                module.reload();

                // script
                FileFileMonitor.FILE_MONITOR_EVENT.forEach(script -> SBPlugin.getScriptManager().performScript(script.replace("%file%", file.getName()), Bukkit.getServer().getConsoleSender()));
            }
        });
    }

    @Override
    public void onOverflow(File file) {

    }
}