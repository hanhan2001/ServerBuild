package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.filemonitorcommand.FileMonitorCommand;
import me.xiaoying.sb.constant.FileMonitorConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.listeners.FileMonitorListener;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitorHandle implements Handle {
    FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(ServerUtil.getDataFolder());
    FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500, fileAlterationObserver);

    {
        this.fileAlterationObserver.addListener(new FileMonitorListener());
    }

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
        stop();

        ServerBuild.getFileService().file("FileMonitor");
        ServerBuild.getFileService().init("FileMonitor");

        if (!this.enable()) {
            PluginUtil.unregisterCommand("fm", ServerBuild.getInstance());
            return;
        }

        try {
            this.fileAlterationMonitor.start();
        } catch (Exception e) {
            //
        }

        ServerUtil.registerCommand("fm", new FileMonitorCommand());
    }

    @Override
    public void stop() {
        try {
            this.fileAlterationMonitor.stop();
        } catch (Exception e) {
            //
        }
    }
}
