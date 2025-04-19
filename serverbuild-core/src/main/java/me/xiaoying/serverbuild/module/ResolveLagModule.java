package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.resolvelag.ResolveLagCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLagEntityChecker;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLagEntityClear;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagEntityCheckerManager;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagEntityClearManager;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagMemoryManager;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagTPSManager;
import me.xiaoying.serverbuild.scheduler.resolvelag.ResolveLagEntityCheckerScheduler;
import me.xiaoying.serverbuild.scheduler.resolvelag.ResolveLagEntityClearScheduler;
import me.xiaoying.serverbuild.scheduler.resolvelag.ResolveLagTPSScheduler;

public class ResolveLagModule extends Module {
    private ResolveLagEntityClearManager entityClearManager;
    private ResolveLagEntityCheckerManager entityCheckerManager;

    private ResolveLagMemoryManager memoryManager;
    private ResolveLagTPSManager tpsManager;

    private double[] tps = {20.0, 20.0, 20.0};

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

        this.memoryManager = new ResolveLagMemoryManager();
        this.tpsManager = new ResolveLagTPSManager();

        // register files
        this.registerFile(new FileResolveLag());
        this.registerFile(new FileResolveLagEntityChecker());
        this.registerFile(new FileResolveLagEntityClear());

        // register commands
        this.registerCommand(new ResolveLagCommand());

        // register scheduler
        this.registerScheduler(new ResolveLagTPSScheduler());
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

    public void setTPS(double[] tps) {
        this.tps = tps;
    }

    public double[] getTPS() {
        return this.tps;
    }

    public ResolveLagEntityClearManager getEntityClearManager() {
        return this.entityClearManager;
    }

    public ResolveLagEntityCheckerManager getEntityCheckerManager() {
        return this.entityCheckerManager;
    }

    public ResolveLagMemoryManager getMemoryManager() {
        return this.memoryManager;
    }

    public ResolveLagTPSManager getTpsManager() {
        return this.tpsManager;
    }
}