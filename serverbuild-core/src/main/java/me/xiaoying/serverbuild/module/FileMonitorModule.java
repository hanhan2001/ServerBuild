package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileFileMonitor;
import me.xiaoying.serverbuild.manager.FileMonitorManager;

/**
 * Module FileMonitor
 */
public class FileMonitorModule extends Module {
    private FileMonitorManager fileMonitorManager;

    @Override
    public String getName() {
        return "文件监控";
    }

    @Override
    public String getAliasName() {
        return "FileMonitor";
    }

    @Override
    public boolean ready() {
        return FileFileMonitor.ENABLE;
    }

    @Override
    public void init() {
        this.registerFile(new FileFileMonitor());
    }

    @Override
    public void onEnable() {
        this.fileMonitorManager = new FileMonitorManager();

        this.fileMonitorManager.registerMonitorPath(SBPlugin.getInstance().getDataFolder().getAbsolutePath());
    }

    @Override
    public void onDisable() {
        this.fileMonitorManager.close();
    }

    public FileMonitorManager getFileMonitorManager() {
        return this.fileMonitorManager;
    }
}