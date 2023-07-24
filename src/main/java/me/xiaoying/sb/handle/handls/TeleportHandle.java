package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.teleportcommand.TeleportCommand;
import me.xiaoying.sb.constant.TeleportConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;

public class TeleportHandle implements Handle {
    @Override
    public boolean enable() {
        return TeleportConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();

        ServerUtil.sendMessage("&b|    &a传送(Teleport): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("tp", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a传送(Teleport): &c未开启", true);
    }


    @Override
    public void reload() {
        stop();

        ServerBuild.getFileService().file("Teleport");
        ServerBuild.getFileService().init("Teleport");

        if (!this.enable())
            return;

        ServerUtil.registerCommand("tp", new TeleportCommand());
    }

    @Override
    public void stop() {
        PluginUtil.unregisterCommand("tp", ServerBuild.getInstance());
    }
}