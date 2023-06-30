package me.xiaoying.sb.service;

import me.xiaoying.sb.entity.WelcomeMessageEntity;

import java.util.HashSet;
import java.util.Set;

public class WelcomeMessageService {
    private static final Set<WelcomeMessageEntity> welcomeMessageEntities  = new HashSet<>();

    public static void registerWelcomeMessage(WelcomeMessageEntity welcomeMessageEntity) {
        welcomeMessageEntities.add(welcomeMessageEntity);
    }

    public static void unregisterWelcomeMessage(String key) {
        for (WelcomeMessageEntity welcomeMessageEntity : welcomeMessageEntities) {
            if (!key.equalsIgnoreCase(welcomeMessageEntity.getKey()))
                continue;

            welcomeMessageEntities.remove(welcomeMessageEntity);
        }
    }

    public static void unregisterWelcomeMessages() {
        welcomeMessageEntities.clear();
    }

    public static Set<WelcomeMessageEntity> getWelcomeMessageEntities() {
        return welcomeMessageEntities;
    }

    public static WelcomeMessageEntity getWelcomeMessageEntity(String key) {
        WelcomeMessageEntity entity = null;
        for (WelcomeMessageEntity welcomeMessageEntity : welcomeMessageEntities) {
            if (!key.equalsIgnoreCase(welcomeMessageEntity.getKey()))
                continue;

            entity = welcomeMessageEntity;
        }

        return entity;
    }
}