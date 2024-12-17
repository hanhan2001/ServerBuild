package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.autorespawn.AutoRespawnCommand;
import me.xiaoying.serverbuild.entity.AutoRespawnEntity;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.listener.AutoRespawnListener;
import me.xiaoying.serverbuild.scheduler.AutoRespawnScheduler;
import me.xiaoying.serverbuild.utils.YamlUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoRespawnModule extends Module {
    private FileAutoRespawn file;
    private final List<AutoRespawnEntity> autoRespawnEntities = new ArrayList<>();

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
        this.file = new FileAutoRespawn();
        this.registerFile(this.file);

        YamlUtil.getNodes("AutoRespawn", "Group").forEach(object -> {
            String string = object.toString();

            this.autoRespawnEntities.add(new AutoRespawnEntity(this.file.getConfiguration().getString("AutoRespawn.Group." + string + ".Permission"), this.file.getConfiguration().getInt("AutoRespawn.Group." + string + ".Priority"), Collections.singletonList(this.file.getConfiguration().getString("AutoRespawn.Group." + string + ".Scripts"))));
        });

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

    public List<AutoRespawnEntity> getAutoRespawnEntities() {
        return this.autoRespawnEntities;
    }
}