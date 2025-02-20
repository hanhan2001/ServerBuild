package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.resolvelag.ResolveLagCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLagEntityChecker;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLagEntityClear;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagEntityCheckerManager;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagEntityClearManager;
import me.xiaoying.serverbuild.scheduler.resolvelag.ResolveLagEntityCheckerScheduler;
import me.xiaoying.serverbuild.scheduler.resolvelag.ResolveLagEntityClearScheduler;

public class ResolveLagModule extends Module {
    private ResolveLagEntityClearManager entityClearManager;
    private ResolveLagEntityCheckerManager entityCheckerManager;

    @Override
    public String getName() {
        return "服务器清理";
    }

    @Override
    public String getAliasName() {
        return "ResolveLag";
    }

    @Override
    public boolean ready() {
        return FileResolveLag.ENABLE;
    }

    @Override
    public void init() {
        this.entityClearManager = new ResolveLagEntityClearManager();
        this.entityCheckerManager = new ResolveLagEntityCheckerManager();

        // register files
        this.registerFile(new FileResolveLag());
        this.registerFile(new FileResolveLagEntityChecker());
        this.registerFile(new FileResolveLagEntityClear());

        // register commands
        this.registerCommand(new ResolveLagCommand());

        // register scheduler
        if (FileResolveLagEntityClear.ENABLE)
            this.registerScheduler(new ResolveLagEntityClearScheduler());

        if (FileResolveLagEntityChecker.ENABLE)
            this.registerScheduler(new ResolveLagEntityCheckerScheduler());
    }

    @Override
    public void onEnable() {
        ((FileMonitorModule) SBPlugin.getModuleManager().getModule("FileMonitor")).getFileMonitorManager().registerMonitorPath(SBPlugin.getInstance().getDataFolder() + "/ResolveLag");
    }

    @Override
    public void onDisable() {

    }

    public ResolveLagEntityClearManager getEntityClearManager() {
        return this.entityClearManager;
    }
    public ResolveLagEntityCheckerManager getEntityCheckerManager() {
        return this.entityCheckerManager;
    }
}