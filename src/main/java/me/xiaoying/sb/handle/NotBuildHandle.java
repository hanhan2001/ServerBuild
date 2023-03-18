package me.xiaoying.sb.handle;

import me.xiaoying.sb.command.notbuildcommand.NotBuildCommand;
import me.xiaoying.sb.files.config.FileNotBuild;
import me.xiaoying.sb.listener.NotBuildListener;
import me.xiaoying.sb.utils.ServerUtil;

public class NotBuildHandle implements Handle {
    @Override
    public boolean enable() {
        return FileNotBuild.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        ServerUtil.registerEvent(new NotBuildListener());
        ServerUtil.registerCommand("nb", new NotBuildCommand());
        ServerUtil.sendMessage("&b|    &a禁止建筑(NotBuild): &e已开启", true);
    }

    @Override
    public void onDisable() {
        ServerUtil.sendMessage("&b|    &a禁止建筑(NotBuild): &c未开启", true);
    }

    @Override
    public void reload() {
        FileNotBuild.fileNotBuild();
        ServerUtil.registerEvent(new NotBuildListener());
        ServerUtil.registerCommand("nb", new NotBuildCommand());
    }
}