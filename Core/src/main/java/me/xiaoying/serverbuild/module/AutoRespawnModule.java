package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.autorespawn.AutoRespawnCommand;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.listener.AutoRespawnListener;
import me.xiaoying.serverbuild.scheduler.AutoRespawnScheduler;

public class AutoRespawnModule extends Module {
    @Override
    public String getName() {
        return "自动重生";
    }

    @Override
    public String getAliasName() {
        return "AutoRespawn";
    }

    @Override
    public boolean ready() {
        return FileAutoRespawn.ENABLE;
    }

    @Override
    public void init() {
        // file
        this.registerFile(new FileAutoRespawn());

        // commands
        this.registerCommand(new AutoRespawnCommand());

        // listener or scheduler
        if (FileAutoRespawn.AUTO_RESPAWN_TYPE.equalsIgnoreCase("Player"))
            this.registerListener(new AutoRespawnListener());
        else
            this.registerScheduler(new AutoRespawnScheduler());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}