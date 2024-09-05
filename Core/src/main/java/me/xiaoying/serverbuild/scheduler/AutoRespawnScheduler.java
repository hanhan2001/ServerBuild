package me.xiaoying.serverbuild.scheduler;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.utils.ServerUtil;

public class AutoRespawnScheduler extends Scheduler {
    public AutoRespawnScheduler() {
        super(Type.SYNC_REPEAT, FileAutoRespawn.AUTO_RESPAWN_SERVER);
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            ServerUtil.getOnlinePlayers().forEach(player -> {
                if (!player.isDead())
                    return;

                player.spigot().respawn();
                for (String s : FileAutoRespawn.AUTO_RESPAWN_SCRIPT.split("\n"))
                    SBPlugin.getScriptManager().performScript(new VariableFactory(s).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).player(player).placeholder(player).color().toString(), player);
            });
        };
    }
}