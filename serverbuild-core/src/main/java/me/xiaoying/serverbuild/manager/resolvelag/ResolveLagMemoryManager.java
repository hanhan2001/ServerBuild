package me.xiaoying.serverbuild.manager.resolvelag;

import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagMemoryEntity;

import java.util.ArrayList;
import java.util.List;

public class ResolveLagMemoryManager {
    private final List<ResolveLagMemoryEntity> now_entities = new ArrayList<>();
    private final List<ResolveLagMemoryEntity> idle_entities = new ArrayList<>();

    public void addNow(ResolveLagMemoryEntity entity) {
        if (this.now_entities.contains(entity))
            return;

        this.now_entities.add(entity);
    }

    public List<ResolveLagMemoryEntity> getNowEntities() {
        return this.now_entities;
    }

    public void addIdle(ResolveLagMemoryEntity entity) {
        if (this.idle_entities.contains(entity))
            return;

        this.idle_entities.add(entity);
    }

    public List<ResolveLagMemoryEntity> getIdleEntities() {
        return this.idle_entities;
    }

    public void clear() {
        this.now_entities.clear();
        this.idle_entities.clear();
    }
}