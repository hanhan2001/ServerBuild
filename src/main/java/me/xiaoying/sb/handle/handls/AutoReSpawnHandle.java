package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;

/**
 * 处理 AutoReSpawn
 */
public class AutoReSpawnHandle implements Handle {
    @Override
    public boolean enable() {
        return false;
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

    }
}