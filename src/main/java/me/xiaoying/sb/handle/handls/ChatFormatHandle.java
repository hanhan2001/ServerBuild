package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.chatformatcommand.ChatFormatCommand;
import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.entity.ChatFormatEntity;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.listeners.ChatFormatListener;
import me.xiaoying.sb.playerdata.data.ChatFormatPlayerData;
import me.xiaoying.sb.service.ChatFormatService;
import me.xiaoying.sb.task.tasks.ChatFormatTask;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;

public class ChatFormatHandle implements Handle {

    @Override
    public boolean enable() {
        return ChatFormatConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();

        ServerUtil.sendMessage("&b|    &a聊天格式(ChatFormat): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("cf", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a聊天格式(ChatFormat): &c未开启", true);
    }

    @Override
    public void reload() {
        stop();

        ServerBuild.getFileService().file("ChatFormat");
        ServerBuild.getFileService().init("ChatFormat");

        if (!this.enable())
            this.stop();

        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/ChatFormat.yml", "Formats").forEach(string -> ChatFormatService.registerChatFormat(new ChatFormatEntity(string)));
        ServerBuild.getPlayerDataService().registerPlayerData("chatformat", new ChatFormatPlayerData());
        ServerBuild.getPlayerDataService().filePlayerData("chatformat");

        ServerBuild.getListenerService().registerListener(this, new ChatFormatListener());
        ServerBuild.getListenerService().runListeners(this);
        ServerBuild.getTaskServer().registerTask(this, new ChatFormatTask());
        ServerBuild.getTaskServer().runTasks(this);
        ServerUtil.registerCommand("cf", new ChatFormatCommand());
    }

    @Override
    public void stop() {
        PluginUtil.unregisterCommand("cf", ServerBuild.getInstance());

        ChatFormatService.unregisterChatFormats();
        if (ServerBuild.getListenerService().getListeners(this) != null)
            ServerBuild.getListenerService().unregisterListener(this);
        if (ServerBuild.getTaskServer().getTasks(this) != null)
            ServerBuild.getTaskServer().unregisterTasks(this);
    }
}