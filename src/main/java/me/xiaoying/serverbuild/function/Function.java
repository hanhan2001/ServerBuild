package me.xiaoying.serverbuild.function;

import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.file.SubFile;
import org.bukkit.event.Listener;

import java.util.List;

public interface Function {
    String getName();
    String getAliasName();
    String getDescription();

    boolean enable();
    void onEnable();
    void onDisable();
    void run();
    void stop();

    List<SubFile> getFiles();
    List<Listener> getListeners();
}