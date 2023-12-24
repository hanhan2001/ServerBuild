package me.xiaoying.serverbuild.function.functions;

import me.xiaoying.serverbuild.constant.ConstantFileMonitor;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.file.file.FileFileMonitor;
import me.xiaoying.serverbuild.function.Function;
import me.xiaoying.serverbuild.listener.FileMonitorListener;
import me.xiaoying.serverbuild.task.SubTask;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Function FileMonitor
 */
public class FileMonitorFunction implements Function {
    List<SubFile> files = new ArrayList<>();
    List<SubTask> tasks = new ArrayList<>();
    FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(ServerUtil.getDataFolder());
    FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500, fileAlterationObserver);

    {
        this.fileAlterationObserver.addListener(new FileMonitorListener());
        this.files.add(new FileFileMonitor());
    }

    @Override
    public String getName() {
        return "FileMonitor";
    }

    @Override
    public String getAliasName() {
        return "文件监测器";
    }

    @Override
    public String getDescription() {
        return "检测文件变动并做出相应操作";
    }

    @Override
    public boolean enable() {
        SubFile subFile = null;
        for (SubFile file : this.files) {
            if (!file.getName().equalsIgnoreCase("FileMonitor.yml"))
                continue;

            subFile = file;
        }
        return ((ConstantFileMonitor) subFile.getConstant()).ENABLE_FILEMONITOR;
    }

    @Override
    public void onEnable() {
        try {
            this.fileAlterationMonitor.start();
        } catch (Exception e) {
            // do something
        }
    }

    @Override
    public void onDisable() {
        try {
            this.fileAlterationMonitor.stop();
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void reload() {
        this.onDisable();

        this.files.clear();

        this.files.add(new FileFileMonitor());

        for (SubFile file : this.getFiles()) {
            file.file();
            file.initialize();
        }
    }

    @Override
    public List<String> getCommands() {
        return null;
    }

    @Override
    public List<SubFile> getFiles() {
        return this.files;
    }

    @Override
    public List<SubTask> getTasks() {
        return this.tasks;
    }

    @Override
    public List<Listener> getListeners() {
        return null;
    }
}