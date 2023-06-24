package me.xiaoying.sb.service;

import me.xiaoying.sb.entity.ChatFormatEntity;

import java.util.HashSet;
import java.util.Set;

public class ChatFormatService {
    private static final Set<ChatFormatEntity> chatFormatEntities  = new HashSet<>();

    public static void registerChatFormat(ChatFormatEntity entity) {
        chatFormatEntities.add(entity);
    }

    public static void unregisterChatFormats() {
        chatFormatEntities.clear();
    }

    public static void unregisterChatFormat(String key) {
        for (ChatFormatEntity chatFormatEntity : chatFormatEntities) {
            if (!chatFormatEntity.getKey().equalsIgnoreCase(key))
                continue;

            chatFormatEntities.remove(chatFormatEntity);
        }
    }

    public static void unregisterChatFormat(ChatFormatEntity chatFormat) {
        if (!chatFormatEntities.contains(chatFormat))
            return;

        chatFormatEntities.remove(chatFormat);
    }

    public static Set<ChatFormatEntity> getChatFormatEntities() {
        return chatFormatEntities;
    }

    public static ChatFormatEntity getChatFormatEntity(String key) {
        for (ChatFormatEntity chatFormatEntity : chatFormatEntities) {
            if (!chatFormatEntity.getKey().equalsIgnoreCase(key))
                continue;

            return chatFormatEntity;
        }
        return null;
    }
}