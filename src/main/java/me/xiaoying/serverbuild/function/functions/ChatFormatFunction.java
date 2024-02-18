package me.xiaoying.serverbuild.function.functions;

import me.xiaoying.serverbuild.constant.ConstantChatFormat;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.file.file.FileAutoReSpawn;
import me.xiaoying.serverbuild.file.file.FileChatFormat;
import me.xiaoying.serverbuild.function.Function;
import me.xiaoying.serverbuild.listener.ChatFormatListener;
import me.xiaoying.serverbuild.task.SubTask;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Function ChatFormat
 */
public class ChatFormatFunction implements Function {
    List<SubFile> files = new ArrayList<>();
    List<Listener> listeners = new ArrayList<>();

    {
        this.files.add(new FileChatFormat());
    }

    @Override
    public String getName() {
        return "ChatFormat";
    }

    @Override
    public String getAliasName() {
        return "聊天格式";
    }

    @Override
    public String getDescription() {
        return "修改原版聊天格式";
    }

    @Override
    public boolean enable() {
        SubFile subFile = null;
        for (SubFile file : this.files) {
            if (!file.getName().equalsIgnoreCase("ChatFormat.yml"))
                continue;

            subFile = file;
        }
        return ((ConstantChatFormat) subFile.getConstant()).ENABLE_CHATFORMAT;
    }

    @Override
    public void onEnable() {
        Listener listener = new ChatFormatListener();
        ServerUtil.registerEvent(listener);
        this.listeners.add(listener);
    }

    @Override
    public void onDisable() {
        // 取消注册 监听
        for (Listener listener : this.getListeners())
            ServerUtil.unregisterListener(listener);
    }

    @Override
    public void reload() {
        this.files.clear();

        for (Listener listener : this.listeners)
            ServerUtil.unregisterListener(listener);

        this.files.add(new FileAutoReSpawn());
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
        return null;
    }

    @Override
    public List<Listener> getListeners() {
        return this.listeners;
    }
}