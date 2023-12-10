package me.xiaoying.serverbuild.function;

import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.task.SubTask;
import org.bukkit.event.Listener;

import java.util.List;

public interface Function {
    String getName();
    String getAliasName();
    String getDescription();

    boolean enable();
    void onEnable();
    void onDisable();
    void reload();

    List<String> getCommands();
    List<SubFile> getFiles();
    List<SubTask> getTasks();
    List<Listener> getListeners();
}