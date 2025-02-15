package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.messageannouncer.MessageAnnouncerCommand;
import me.xiaoying.serverbuild.entity.MessageAnnouncerEntity;
import me.xiaoying.serverbuild.file.SFile;
import me.xiaoying.serverbuild.file.FileMessageAnnouncer;
import me.xiaoying.serverbuild.scheduler.MessageAnnouncerScheduler;
import me.xiaoying.serverbuild.utils.YamlUtil;

import java.util.*;

/**
 * Module MessageAnnouncer
 */
public class MessageAnnouncerModule extends Module {
    private SFile SFile;
    private final List<MessageAnnouncerEntity> announcers = new ArrayList<>();

    @Override
    public String getName() {
        return "自动公告";
    }

    @Override
    public String getAliasName() {
        return "MessageAnnouncer";
    }

    @Override
    public boolean ready() {
        return FileMessageAnnouncer.ENABLE;
    }

    @Override
    public void init() {
        // register files
        this.SFile = new FileMessageAnnouncer();
        this.registerFile(this.SFile);

        // command
        this.registerCommand(new MessageAnnouncerCommand());

        // scheduler
        this.registerScheduler(new MessageAnnouncerScheduler());
    }

    @Override
    public void onEnable() {
        YamlUtil.getNodes(this.SFile.getFile(), "MessageAnnouncer.Announcers").forEach(object -> {
            String string = object.toString();
            List<String> scripts = new ArrayList<>(Arrays.asList(Objects.requireNonNull(this.SFile.getConfiguration().getString("MessageAnnouncer.Announcers." + string + ".Script")).split("\n")));
            this.announcers.add(new MessageAnnouncerEntity(string, scripts));
        });
    }

    @Override
    public void onDisable() {
        this.announcers.clear();
    }

    public List<MessageAnnouncerEntity> getAnnouncers() {
        return this.announcers;
    }
}