package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.autorespawn.AutoRespawnCommand;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.listener.AutoRespawnListener;
import me.xiaoying.serverbuild.scheduler.AutoRespawnScheduler;

public class AutoRespawnModule extends Module {
    @Override
    public String getName() {
        return "AutoRespawn";
    }

    @Override
    public String getAliasName() {
        return "自动重生";
    }

    @Override
    public boolean ready() {
        return false;
    }

    @Override
    public void init() {
        // file
        this.registerFile(new FileAutoRespawn());

        // commands
        this.registerCommand(new AutoRespawnCommand());

        // listener
        this.registerListener(new AutoRespawnListener());

        // scheduler
        this.registerScheduler(new AutoRespawnScheduler());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}