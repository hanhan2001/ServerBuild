package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.filemonitorcommand.FileMonitorCommand;
import me.xiaoying.sb.constant.FileMonitorConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.listeners.FileMonitorListener;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;

public class FileMonitorHandle implements Handle {
    @Override
    public boolean enable() {
        return FileMonitorConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();

        ServerUtil.sendMessage("&b|    &a文件检测(FileMonitor): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("fm", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a文件检测(FileMonitor): &c已关闭", true);
    }

    @Override
    public void reload() {
        if (FileMonitorConstant.MONITOR_ENABLE) {
            try {
                FileMonitorConstant.fileAlterationMonitor.stop(0);
                FileMonitorConstant.fileAlterationMonitor.removeObserver(FileMonitorConstant.fileAlterationObserver);
                FileMonitorConstant.MONITOR_ENABLE = false;
            } catch (Exception e) {
                //
            }
        }

        if (!this.enable()) {
            PluginUtil.unregisterCommand("fm", ServerBuild.getInstance());
            try {
                FileMonitorConstant.fileAlterationMonitor.removeObserver(FileMonitorConstant.fileAlterationObserver);
                FileMonitorConstant.fileAlterationMonitor.stop();
                FileMonitorConstant.MONITOR_ENABLE = false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        if (!FileMonitorConstant.MONITOR_ENABLE) {
            try {
                FileMonitorConstant.fileAlterationObserver.addListener(new FileMonitorListener());
                FileMonitorConstant.fileAlterationMonitor.start();
                FileMonitorConstant.MONITOR_ENABLE = true;
            } catch (Exception e) {
                //
            }
        }

        ServerUtil.registerCommand("fm", new FileMonitorCommand());
    }
}
