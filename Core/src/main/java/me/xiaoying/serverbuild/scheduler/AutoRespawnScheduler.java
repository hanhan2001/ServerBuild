package me.xiaoying.serverbuild.scheduler;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.AutoRespawnEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.module.AutoRespawnModule;
import me.xiaoying.serverbuild.utils.ServerUtil;

public class AutoRespawnScheduler extends Scheduler {
    public AutoRespawnScheduler() {
        super(Type.SYNC_REPEAT, FileAutoRespawn.AUTO_RESPAWN_SERVER);
    }

    @Override
    public Runnable getRunnable() {
        return () -> ServerUtil.getOnlinePlayers().forEach(player -> {
            if (!player.isDead())
                return;

            AutoRespawnEntity autoRespawnEntity = null;

            AutoRespawnModule module = (AutoRespawnModule) SBPlugin.getModuleManager().getModule("AutoRespawn");
            for (AutoRespawnEntity entity : module.getAutoRespawnEntities()) {
                if (!entity.useful(player))
                    continue;

                if (autoRespawnEntity == null) {
                    autoRespawnEntity = entity;
                    continue;
                }

                if (autoRespawnEntity.getPriority() < entity.getPriority())
                    continue;

                autoRespawnEntity = entity;
            }

            player.spigot().respawn();

            if (autoRespawnEntity == null)
                return;
            for (String s : autoRespawnEntity.getScripts())
                SBPlugin.getScriptManager().performScript(new VariableFactory(s).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).player(player).placeholder(player).color().toString(), player);
        });
    }
}