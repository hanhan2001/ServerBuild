package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileFileMonitor;
import me.xiaoying.serverbuild.file.SFile;
import me.xiaoying.serverbuild.inf.FileWatcherInterface;
import me.xiaoying.serverbuild.module.Module;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        List<Module> modules = new ArrayList<>();

        SBPlugin.getModuleManager().getModules().forEach(module -> {
            for (SFile moduleSFile : module.getFiles()) {
                if (!moduleSFile.getName().equalsIgnoreCase(file.getName()))
                    return;

                modules.add(module);
                module.reload();

                // script
                FileFileMonitor.FILE_MONITOR_EVENT.forEach(script -> SBPlugin.getScriptManager().performScript(script, Bukkit.getServer().getConsoleSender()));
            }
        });
    }

    @Override
    public void onOverflow(File file) {

    }
}