package me.xiaoying.serverbuild.entity.resolvelag;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ResolveLagEntityCheckerEntity {
    private final String key;
    private final int limit;
    private final int min;

    public ResolveLagEntityCheckerEntity(String key, int limit, int min) {
        this.key = key;
        this.limit = limit;
        this.min = min;
    }

    public String getKey() {
        return this.key;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getMin() {
        return this.min;
    }

    public int clear(List<Entity> entities) {
        int excludeAmount = 0;
        boolean exceeds = false;
        int removed = 0;

        List<Entity> matchedEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.isDead() || !this.match(entity))
                continue;

            excludeAmount++;
            matchedEntities.add(entity);

            if (excludeAmount < this.getLimit())
                continue;

            exceeds = true;
        }

        if (!exceeds)
            return 0;

        for (Entity matchedEntity : matchedEntities) {
            if (excludeAmount <= this.getMin())
                return removed;

            matchedEntity.remove();

            excludeAmount--;
            removed++;
        }

        return removed;
    }

    public boolean match(Entity entity) {
        if (this.getKey().contains("."))
            return entity.getClass().getName().equalsIgnoreCase(this.getKey());
        else
            return entity.getType().toString().equalsIgnoreCase(this.getKey());
    }
}