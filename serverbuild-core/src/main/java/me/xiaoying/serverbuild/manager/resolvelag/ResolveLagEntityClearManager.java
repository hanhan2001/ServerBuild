package me.xiaoying.serverbuild.manager.resolvelag;

import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagClearDownEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagCountTimeEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagFilterEntity;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResolveLagEntityClearManager {
    private final List<ResolveLagFilterEntity> filters;
    private final List<ResolveLagFilterEntity> matcher;

    private final Map<Integer, ResolveLagCountTimeEntity> countTimes;
    private final List<ResolveLagClearDownEntity> clearDowns;

    public ResolveLagEntityClearManager() {
        this.filters = new ArrayList<>();
        this.matcher = new ArrayList<>();

        this.countTimes = new HashMap<>();
        this.clearDowns = new ArrayList<>();
    }

    /**
     * Add entity filter
     *
     * @param filter ResolveLagFilterEntity
     */
    public void addFilter(ResolveLagFilterEntity filter) {
        if (this.filters.contains(filter))
            return;

        this.filters.add(filter);
    }

    /**
     * Add entity matcher
     *
     * @param matcher ResolveLagFilterEntity
     */
    public void addMatcher(ResolveLagFilterEntity matcher) {
        if (this.matcher.contains(matcher))
            return;

        this.matcher.add(matcher);
    }

    /**
     * Get all registered entity filter
     *
     * @return ArrayList
     */
    public List<ResolveLagFilterEntity> getFilters() {
        return this.filters;
    }

    /**
     * Get all registered entity matcher
     *
     * @return ArrayList
     */
    public List<ResolveLagFilterEntity> getMatcher() {
        return this.matcher;
    }

    /**
     * Add clear down
     *
     * @param clearDownEntity ResolveLagClearDownEntity
     */
    public void addClearDown(ResolveLagClearDownEntity clearDownEntity) {
        if (this.clearDowns.contains(clearDownEntity))
            return;

        this.clearDowns.add(clearDownEntity);
    }

    /**
     * Get all registered clear down
     *
     * @return ArrayList
     */
    public List<ResolveLagClearDownEntity> getClearDowns() {
        return this.clearDowns;
    }

    /**
     * Add count time
     *
     * @param countTimeEntity ResolveLagCountTimeEntity
     */
    public void addCountTime(ResolveLagCountTimeEntity countTimeEntity) {
        if (this.countTimes.containsKey(countTimeEntity.getTime()))
            return;

        this.countTimes.put(countTimeEntity.getTime(), countTimeEntity);
    }

    /**
     * Get ResolveLagCountTimeEntity by time
     *
     * @param time countdown
     * @return ResolveLagCountTimeEntity
     */
    public ResolveLagCountTimeEntity getCountTime(int time) {
        return this.countTimes.get(time);
    }

    /**
     * Get all registered ResolveLagCountTimeEntity
     *
     * @return ArrayList
     */
    public List<ResolveLagCountTimeEntity> getCountTimes() {
        return (List<ResolveLagCountTimeEntity>) this.countTimes.values();
    }
}