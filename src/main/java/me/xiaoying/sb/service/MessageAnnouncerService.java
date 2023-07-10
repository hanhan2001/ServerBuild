package me.xiaoying.sb.service;

import me.xiaoying.sb.entity.messageannouncer.ActionbarAnnouncerEntity;
import me.xiaoying.sb.entity.messageannouncer.ChatAnnouncerEntity;
import me.xiaoying.sb.entity.messageannouncer.MessageAnnouncerEntity;
import me.xiaoying.sb.entity.messageannouncer.TitleAnnouncerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Service MessageAnnouncer
 */
public class MessageAnnouncerService {
    static List<MessageAnnouncerEntity> chat = new ArrayList<>();
    static List<MessageAnnouncerEntity> title = new ArrayList<>();
    static List<MessageAnnouncerEntity> actionbar = new ArrayList<>();

    public static void registerAnnouncer(MessageAnnouncerEntity welcomeMessageEntity) {
        if (welcomeMessageEntity instanceof ChatAnnouncerEntity) {
            chat.add(welcomeMessageEntity);
            return;
        }

        if (welcomeMessageEntity instanceof TitleAnnouncerEntity) {
            title.add(welcomeMessageEntity);
            return;
        }

        if (welcomeMessageEntity instanceof ActionbarAnnouncerEntity)
            actionbar.add(welcomeMessageEntity);
    }

    public static void unregisterAnnouncer(MessageAnnouncerEntity messageAnnouncerEntity) {
        chat.remove(messageAnnouncerEntity);
        title.remove(messageAnnouncerEntity);
        actionbar.remove(messageAnnouncerEntity);
    }

    public static void unregisterAnnouncers() {
        chat.clear();
        title.clear();
        actionbar.clear();
    }

    public static List<MessageAnnouncerEntity> getChat() {
        return chat;
    }

    public static List<MessageAnnouncerEntity> getActionbar() {
        return actionbar;
    }

    public static List<MessageAnnouncerEntity> getTitle() {
        return title;
    }
}