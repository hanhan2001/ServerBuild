package me.xiaoying.sb.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Event ClearEntity
 */
public class ClearEntityEvent extends Event {
    List<Item> items;
    List<Entity> entities;

    private static final HandlerList handlers = new HandlerList();

    /**
     * 构造器
     *
     * @param items 清理 物品
     * @param entities 清理 实体
     */
    public ClearEntityEvent(List<Item> items, List<Entity> entities) {
        this.items = items;
        this.entities = entities;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }
}