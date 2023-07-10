package me.xiaoying.sb.entity.messageannouncer;

import org.bukkit.entity.Player;

public abstract class MessageAnnouncerEntity {
    public MessageAnnouncerEntity(String key) {}

    public abstract void send(Player player);
}