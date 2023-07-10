package me.xiaoying.sb.task.tasks;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.MessageAnnouncerConstant;
import me.xiaoying.sb.service.MessageAnnouncerService;
import me.xiaoying.sb.task.TaskHandle;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;

/**
 * 线程 MessageAnnouncer
 */
public class MessageAnnouncerTask implements TaskHandle {
    int chat, title, actionbar;

    int time_chat = 0,
            time_title = 0,
            time_actionbar = 0;

    @Override
    public void run() {
        if (MessageAnnouncerConstant.ENABLE_CHAT_ANNOUNCER) {
            this.chat = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
                if (MessageAnnouncerService.getChat().size() <= this.time_chat)
                    this.time_chat = 0;

                ServerUtil.getOnlinePlayers().forEach(player -> MessageAnnouncerService.getChat().get(this.time_chat).send(player));
                this.time_chat++;
            }, 0L, MessageAnnouncerConstant.DELAY_CHAT_ANNOUNCER);
        }

        if (MessageAnnouncerConstant.ENABLE_TITLE_ANNOUNCER) {
            this.title = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
                if (MessageAnnouncerService.getTitle().size() <= this.time_title)
                    this.time_title = 0;

                ServerUtil.getOnlinePlayers().forEach(player -> MessageAnnouncerService.getTitle().get(this.time_title).send(player));
                this.time_title++;
            }, 0L, MessageAnnouncerConstant.DELAY_TITLE_ANNOUNCER);
        }

        if (MessageAnnouncerConstant.ENABLE_ACTIONBAR_ANNOUNCER) {
            this.actionbar = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> {
                if (MessageAnnouncerService.getActionbar().size() <= this.time_actionbar)
                    this.time_actionbar = 0;

                ServerUtil.getOnlinePlayers().forEach(player -> MessageAnnouncerService.getActionbar().get(this.time_actionbar).send(player));
                this.time_actionbar++;
            }, 0L, MessageAnnouncerConstant.DELAY_ACTIONBAR_ANNOUNCER);
        }
    }

    @Override
    public void stop() {
        try {
            Bukkit.getServer().getScheduler().cancelTask(this.chat);
            Bukkit.getServer().getScheduler().cancelTask(this.title);
            Bukkit.getServer().getScheduler().cancelTask(this.actionbar);
        } catch (Exception e) { /**/ }
    }

    @Override
    public Integer[] getTasks() {
        return new Integer[] {this.chat, this.title, this.actionbar};
    }
}