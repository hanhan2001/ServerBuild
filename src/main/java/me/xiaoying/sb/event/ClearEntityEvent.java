package me.xiaoying.sb.event;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Event ClearEntity
 */
public class ClearEntityEvent extends Event {
    List<Entity> entities;

    private static final HandlerList handlers = new HandlerList();

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * 构造器
     *
     * @param entities 清理对象
     */
    public ClearEntityEvent(List<Entity> entities) {
        this.entities = entities;
    }

    public int getCount() {
        return this.entities.size();
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public List<Entity> getAnimals() {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : this.entities) {
            if (!(entity instanceof Animals))
                continue;

            list.add(entity);
        }
        return list;
    }

    public List<Entity> getItems() {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : this.entities) {
            if (!(entity instanceof Item))
                continue;

            list.add(entity);
        }
        return list;
    }

    public List<Entity> getMonster() {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : this.entities) {
            if (!(entity instanceof Monster))
                continue;

            list.add(entity);
        }
        return list;
    }
}