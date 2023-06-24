package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.notbuildcommand.NotBuildCommand;
import me.xiaoying.sb.files.config.FileNotBuild;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.NotBuildListener;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;

public class NotBuildHandle implements Handle {
    @Override
    public boolean enable() {
        return FileNotBuild.SET_ENABLE;
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
        FileNotBuild.fileNotBuild();

        if (!this.enable()) {
            PluginUtil.unregisterCommand("nb", ServerBuild.getInstance());
            return;
        }

        ServerUtil.registerEvent(new NotBuildListener());
        ServerUtil.registerCommand("nb", new NotBuildCommand());
    }
}