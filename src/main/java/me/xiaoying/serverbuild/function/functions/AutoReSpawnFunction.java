package me.xiaoying.serverbuild.function.functions;

import org.bukkit.event.Listener;
import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.task.SubTask;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.function.Function;
import me.xiaoying.serverbuild.file.file.FileAutoReSpawn;
import me.xiaoying.serverbuild.task.tasks.AutoReSpawnTask;
import me.xiaoying.serverbuild.listener.AutoReSpawnListener;
import me.xiaoying.serverbuild.constant.ConstantAutoReSpawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Function AutoReSpawn
 */
public class AutoReSpawnFunction implements Function {
    List<SubFile> files = new ArrayList<>();
    List<SubTask> tasks = new ArrayList<>();
    List<Listener> listeners = new ArrayList<>();

    {
        this.files.add(new FileAutoReSpawn());
        this.tasks.add(new AutoReSpawnTask());
    }

    @Override
    public String getName() {
        return "AutoReSpawn";
    }

    @Override
    public String getAliasName() {
        return "自动重生";
    }

    @Override
    public String getDescription() {
        return "为玩家自动重生";
    }

    @Override
    public boolean enable() {
        SubFile subFile = null;
        for (SubFile file : this.files) {
            if (!file.getName().equalsIgnoreCase("AutoReSpawn.yml"))
                continue;

            subFile = file;
        }
        return ((ConstantAutoReSpawn) subFile.getConstant()).ENABLE_AUTORESPAWN;
    }

    @Override
    public void onEnable() {
        ConstantAutoReSpawn content = (ConstantAutoReSpawn) this.getFiles().get(0).getConstant();
        switch (content.AUTORESPAWN_TYPE.toUpperCase()) {
            case "SERVER": {
                for (SubTask task : this.getTasks()) {
                    ServerBuild.getTaskService().registerTask(task);
                    task.run();
                }
                break;
            }
            case "PLAYER": {
                Listener listener = new AutoReSpawnListener();
                ServerUtil.registerEvent(listener);
                this.listeners.add(listener);
                break;
            }
        }
    }

    @Override
    public void onDisable() {
        // 取消注册 监听
        for (Listener listener : this.getListeners())
            ServerUtil.unregisterListener(listener);

        // 停止 事件
        for (SubTask task : this.getTasks())
            ServerUtil.cancelTask(task.getId());
    }

    @Override
    public void reload() {
        this.onDisable();

        this.files.clear();
        this.tasks.clear();

        for (Listener listener : this.listeners)
            ServerUtil.unregisterListener(listener);

        this.files.add(new FileAutoReSpawn());
        this.tasks.add(new AutoReSpawnTask());

        for (SubFile file : this.getFiles()) {
            file.file();
            file.initialize();
        }

        this.onEnable();
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
        return this.listeners;
    }
}