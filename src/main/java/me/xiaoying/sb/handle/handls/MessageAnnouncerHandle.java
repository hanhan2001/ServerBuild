package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.messageannouncercommand.MessageAnnouncerCommand;
import me.xiaoying.sb.constant.MessageAnnouncerConstant;
import me.xiaoying.sb.entity.messageannouncer.ActionbarAnnouncerEntity;
import me.xiaoying.sb.entity.messageannouncer.ChatAnnouncerEntity;
import me.xiaoying.sb.entity.messageannouncer.TitleAnnouncerEntity;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.service.MessageAnnouncerService;
import me.xiaoying.sb.task.tasks.MessageAnnouncerTask;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;

/**
 * Handle MessageAnnouncer
 */
public class MessageAnnouncerHandle implements Handle {
    @Override
    public boolean enable() {
        return MessageAnnouncerConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();

        ServerUtil.sendMessage("&b|    &a自动公告(MessageAnnouncer): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("ma", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a自动公告(MessageAnnouncer): &c未开启", true);
    }

    @Override
    public void reload() {
        stop();

        ServerBuild.getFileService().init("MessageAnnouncer");
        ServerBuild.getFileService().file("MessageAnnouncer");

        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/MessageAnnouncer.yml", "MessageAnnouncer.Chat.Message").forEach(string -> MessageAnnouncerService.registerAnnouncer(new ChatAnnouncerEntity(string)));
        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/MessageAnnouncer.yml", "MessageAnnouncer.Title.Message").forEach(string -> MessageAnnouncerService.registerAnnouncer(new TitleAnnouncerEntity(string)));
        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/MessageAnnouncer.yml", "MessageAnnouncer.ActionBar.Message").forEach(string -> MessageAnnouncerService.registerAnnouncer(new ActionbarAnnouncerEntity(string)));

        if (!this.enable()) {
            PluginUtil.unregisterCommand("ma", ServerBuild.getInstance());
            return;
        }

        ServerBuild.getTaskServer().registerTask(this, new MessageAnnouncerTask());
        ServerBuild.getTaskServer().runTasks(this);
        ServerUtil.registerCommand("ma", new MessageAnnouncerCommand());
    }

    @Override
    public void stop() {
        MessageAnnouncerService.unregisterAnnouncers();

        if (ServerBuild.getTaskServer().getTasks(this) != null)
            ServerBuild.getTaskServer().unregisterTasks(this);
    }
}