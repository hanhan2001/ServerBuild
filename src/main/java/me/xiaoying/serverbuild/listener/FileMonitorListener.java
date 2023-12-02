package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.constant.ConstantFileMonitor;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.function.Function;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Listener FileMonitor
 */
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
        // 文件过滤
        if (file.getName().equalsIgnoreCase("ServerBuild.db"))
            return;

        String filename = file.getName().split("\\.")[0];
        if (filename.equalsIgnoreCase("config"))
            return;

        List<Function> functions = new ArrayList<>();
        for (Function function : ServerBuild.getFunctionService().getFunctions()) {
            for (SubFile functionFile : function.getFiles()) {
                if (!functionFile.getName().equalsIgnoreCase(file.getName()))
                    continue;

                functions.add(function);
            }
        }

        // 判断是否为空 Function
        if (functions.size() == 0)
            return;

        functions.forEach(Function::reload);

        ConstantFileMonitor constant = (ConstantFileMonitor) ServerBuild.getFunctionService().getFunction("FileMonitor").getFiles().get(0).getConstant();
        for (String s : constant.FILEMONITOR_EVENT)
            ServerBuild.getScriptManager().callScript(s);
    }

    @Override
    public void onFileDelete(File file) {
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {

    }
}
