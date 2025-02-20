package me.xiaoying.serverbuild.manager.resolvelag;

import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagClearDownEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagEntityCheckerEntity;

import java.util.ArrayList;
import java.util.List;

public class ResolveLagEntityCheckerManager {
    private final List<ResolveLagClearDownEntity> clearDowns;
    private final List<ResolveLagEntityCheckerEntity> checkerEntities;

    public ResolveLagEntityCheckerManager() {
        this.clearDowns = new ArrayList<>();

        this.checkerEntities = new ArrayList<>();
    }

    public void addCheckerEntity(ResolveLagEntityCheckerEntity entityCheckerEntity) {
        if (this.checkerEntities.contains(entityCheckerEntity))
            return;

        this.checkerEntities.add(entityCheckerEntity);
    }

    public List<ResolveLagEntityCheckerEntity> getCheckerEntities() {
        return this.checkerEntities;
    }

    /**
     * Add clear down entity
     *
     * @param clearDownEntity ResolveLagClearDownEntity
     */
    public void addClearDown(ResolveLagClearDownEntity clearDownEntity) {
        if (this.clearDowns.contains(clearDownEntity))
            return;

        this.clearDowns.add(clearDownEntity);
    }

    /**
     * Get clear down entities
     *
     * @return ArrayList
     */
    public List<ResolveLagClearDownEntity> getClearDowns() {
        return this.clearDowns;
    }
}
