package me.xiaoying.serverbuild.function.functions;

import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.function.Function;
import me.xiaoying.serverbuild.task.SubTask;
import org.bukkit.event.Listener;

import java.util.List;

public class WelcomeMessageFunction implements Function {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAliasName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean enable() {
        return false;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void reload() {

    }

    @Override
    public List<String> getCommands() {
        return null;
    }

    @Override
    public List<SubFile> getFiles() {
        return null;
    }

    @Override
    public List<SubTask> getTasks() {
        return null;
    }

    @Override
    public List<Listener> getListeners() {
        return null;
    }
}