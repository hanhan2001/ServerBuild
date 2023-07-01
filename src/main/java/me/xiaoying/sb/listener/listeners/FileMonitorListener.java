package me.xiaoying.sb.listener.listeners;

import me.xiaoying.sb.constant.FileMonitorConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ServerUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.Objects;

public class FileMonitorListener extends FileAlterationListenerAdaptor {
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {

    }

    @Override
    public void onDirectoryCreate(File file) {
    }

    @Override
    public void onDirectoryChange(File file) {
    }

    @Override
    public void onDirectoryDelete(File file) {
    }

    @Override
    public void onFileCreate(File file) {
    }

    @Override
    public void onFileChange(File file) {
        if (FileMonitorConstant.SET_MESSAGE_CONSOLE)
            ServerUtil.sendMessage(new VariableFactory(FileMonitorConstant.MESSAGE_UPDATE)
                    .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                    .file(file.getName())
                    .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT).getString(), true);

        Handler.getHandle(file.getName().split("\\.")[0]).reload();
    }

    @Override
    public void onFileDelete(File file) {
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {

    }
}
