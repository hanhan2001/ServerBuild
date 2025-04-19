package me.xiaoying.serverbuild.manager.resolvelag;

import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagTPSEntity;

import java.util.ArrayList;
import java.util.List;

public class ResolveLagTPSManager {
    private List<ResolveLagTPSEntity> entities = new ArrayList<>();

    public void add(ResolveLagTPSEntity entity) {
        if (this.entities.contains(entity))
            return;

        this.entities.add(entity);
    }

    public List<ResolveLagTPSEntity> getEntities() {
        return this.entities;
    }
}