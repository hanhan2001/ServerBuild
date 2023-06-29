package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.autorespawncommand.AutoReSpawnCommand;
import me.xiaoying.sb.constant.AutoReSpawnConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.listeners.AutoReSpawnListener;
import me.xiaoying.sb.task.tasks.AutoReSpawnTask;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;

/**
 * 处理 AutoReSpawn
 */
public class AutoReSpawnHandle implements Handle {
    @Override
    public boolean enable() {
        return AutoReSpawnConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();
        ServerUtil.sendMessage("&b|    &a自动重生(AutoReSpawn): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("ars", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a自动重生(AutoReSpawn): &c未开启", true);
    }

    @Override
    public void reload() {
        ServerBuild.getFileService().file("AutoReSpawn");
        ServerBuild.getFileService().init("AutoReSpawn");
        if (ServerBuild.getListenerService().getListeners(this) != null)
            ServerBuild.getListenerService().unregisterListener(this);
        if (ServerBuild.getTaskServer().getTasks(this) != null)
            ServerBuild.getTaskServer().unregisterTasks(this);

        if (!this.enable()) {
            PluginUtil.unregisterCommand("ars", ServerBuild.getInstance());
            return;
        }

        ServerBuild.getListenerService().registerListener(this, new AutoReSpawnListener());
        ServerBuild.getListenerService().runListeners(this);
        ServerBuild.getTaskServer().registerTask(this, new AutoReSpawnTask());
        ServerBuild.getTaskServer().runTasks(this);

        ServerUtil.registerCommand("ars", new AutoReSpawnCommand());
    }
}