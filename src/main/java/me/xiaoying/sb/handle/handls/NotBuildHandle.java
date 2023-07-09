package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.notbuildcommand.NotBuildCommand;
import me.xiaoying.sb.constant.NotBuildConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.listeners.NotBuildListener;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;

public class NotBuildHandle implements Handle {
    @Override
    public boolean enable() {
        return NotBuildConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();

        ServerUtil.sendMessage("&b|    &a禁止建筑(NotBuild): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("nb", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a禁止建筑(NotBuild): &c未开启", true);
    }

    @Override
    public void reload() {
        stop();

        ServerBuild.getFileService().file("NotBuild");
        ServerBuild.getFileService().init("NotBuild");

        if (!this.enable()) {
            PluginUtil.unregisterCommand("nb", ServerBuild.getInstance());
            return;
        }

        ServerBuild.getListenerService().registerListener(this, new NotBuildListener());
        ServerBuild.getListenerService().runListeners(this);
        ServerUtil.registerCommand("nb", new NotBuildCommand());
    }

    @Override
    public void stop() {
        if (ServerBuild.getListenerService().getListeners(this) != null)
            ServerBuild.getListenerService().unregisterListener(this);
    }
}