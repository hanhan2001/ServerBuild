package me.xiaoying.serverbuild.scheduler.resolvelag;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagClearDownEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagCountTimeEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagFilterEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLagEntityClear;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagEntityClearManager;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ResolveLagEntityClearScheduler extends Scheduler {
    private int time = FileResolveLagEntityClear.ENTITY_CLEAR_INTERVAL_TIME;
    private final ResolveLagEntityClearManager manager;

    private final Map<ResolveLagFilterEntity, Integer> filterExclude = new HashMap<>();

    public ResolveLagEntityClearScheduler() {
        super(Type.SYNC_REPEAT, 20L);
        this.manager = ((ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag")).getEntityClearManager();
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            // count time
            this.countTime();

            // clear
            if (this.time == 0) {
                // clear
                int amount = 0;
                for (World world : Bukkit.getWorlds())
                    amount += this.clear(world);

                // clear down
                this.clearDown(amount);

                this.filterExclude.clear();

                this.time = FileResolveLagEntityClear.ENTITY_CLEAR_INTERVAL_TIME;
                return;
            }

            this.time--;
        };
    }

    private void countTime() {
        ResolveLagCountTimeEntity countTime;
        if ((countTime = this.manager.getCountTime(this.time)) == null)
            return;

        countTime.getScripts().forEach(string -> SBPlugin.getScriptManager().performScript(new VariableFactory(string)
                        .prefix(FileResolveLag.SETTING_PREFIX)
                        .date(FileResolveLag.SETTING_DATEFORMAT)
                        .time(this.time)
                        .color()
                        .toString()
                , Bukkit.getConsoleSender()));
    }

    public int clear(World world) {
        AtomicInteger count = new AtomicInteger();

        if (FileResolveLagEntityClear.ENTITY_CLEAR_TOTAL_ENABLE && world.getEntities().size() < FileResolveLagEntityClear.ENTITY_CLEAR_TOTAL_LIMIT)
            return 0;

        world.getEntities().forEach(entity -> {
            // exclude player
            if (entity instanceof HumanEntity)
                return;

            int entityCount;
            if (entity instanceof Item)
                entityCount = ((Item) entity).getItemStack().getAmount();
            else
                entityCount = 1;

            boolean removed = true;

            // filter
            for (ResolveLagFilterEntity filter : this.manager.getFilters()) {
                if (!filter.match(entity))
                    continue;

                if (filter.getExclude() <= 0) {
                    removed = false;
                    break;
                }

                this.filterExclude.putIfAbsent(filter, 1);

                if (this.filterExclude.get(filter) > filter.getExclude()) {
                    removed = false;
                    break;
                }

                this.filterExclude.put(filter, this.filterExclude.get(filter) + 1);
            }

            // matcher
            for (ResolveLagFilterEntity filter : this.manager.getMatcher()) {
                if (!removed)
                    break;

                if (!filter.match(entity))
                    continue;

                this.filterExclude.putIfAbsent(filter, 1);

                removed = this.filterExclude.get(filter) > filter.getExclude();

                if (removed)
                    break;

                this.filterExclude.put(filter, this.filterExclude.get(filter) + 1);
            }

            if (!removed)
                return;

            count.set(count.get() + entityCount);
            entity.remove();
        });
        return count.get();
    }

    public void clearDown(int amount) {
        ResolveLagClearDownEntity clearDown = null;

        for (ResolveLagClearDownEntity c : this.manager.getClearDowns()) {
            if (!c.match(amount))
                continue;

            if (clearDown == null) {
                clearDown = c;
                continue;
            }

            if ((c.getType().equalsIgnoreCase("<=") || c.getType().equalsIgnoreCase("<")) && c.getCount() < clearDown.getCount())
                clearDown = c;

            if ((c.getType().equalsIgnoreCase(">=") || c.getType().equalsIgnoreCase(">")) && c.getCount() > clearDown.getCount())
                clearDown = c;
        }

        if (clearDown == null)
            return;

        clearDown.getScripts().forEach(string -> SBPlugin.getScriptManager().performScript(new VariableFactory(string)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .amount(amount)
                .color()
                .toString(), Bukkit.getServer().getConsoleSender()));
    }
}