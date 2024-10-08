package me.xiaoying.serverbuild.scheduler;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.ResolveLagEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileResolveLag;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.utils.ListUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Scheduler ResolveLag
 */
public class ResolveLagScheduler extends Scheduler {
    private int time = FileResolveLag.RESOLVE_LAG_SECOND_TIME;
    private int chunkTime = 0;
    private final ResolveLagModule resolveLagModule = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");

    public ResolveLagScheduler() {
        super(Type.SYNC_REPEAT, 20L);
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            for (Integer i : FileResolveLag.RESOLVE_LAG_COUNT_TIME) {
                if (i != this.time)
                    continue;

                ServerUtil.getOnlinePlayers().forEach(player -> player.sendMessage(new VariableFactory(FileResolveLag.CLEAR_MESSAGE_COUNT_DOWN)
                        .prefix(FileResolveLag.SETTING_PREFIX)
                        .date(FileResolveLag.SETTING_DATEFORMAT)
                        .time(this.time)
                        .player(player)
                        .placeholder(player)
                        .color()
                        .toString()));
            }

            if (this.chunkTime >= FileResolveLag.RESOLVE_LAG_CHUNK_INTERVAL) {
                this.clearChunk();
                this.chunkTime = 0;
                return;
            }

            if (this.time > 0) {
                this.time--;
                return;
            }

            int entitiesCount = this.clearEntity();
            int chunksCount = this.clearChunk();
            int count = entitiesCount + chunksCount;
            boolean hasBigOperator = false;
            List<ResolveLagEntity> trueNode = new ArrayList<>();
            for (ResolveLagEntity clearDown : this.resolveLagModule.getResolveLagEntities()) {
                if (!this.compareInteger(count, clearDown.getId(), clearDown.getType()))
                    continue;

                // 当满足多个条件判断用最大值还是最小值
                if (clearDown.getType().contains(">"))
                    hasBigOperator = true;

                trueNode.add(clearDown);
            }

            List<Integer> list = new ArrayList<>();
            for (ResolveLagEntity clearEntityEntity : trueNode)
                list.add(clearEntityEntity.getId());

            if (list.size() == 0)
                return;

            int time;
            if (hasBigOperator)
                time = ListUtil.getListMaxNumber(list);
            else
                time = ListUtil.getListMinNumber(list);

            ResolveLagEntity entity = null;
            for (ResolveLagEntity clearEntityEntity : trueNode) {
                if (time != clearEntityEntity.getId())
                    continue;

                entity = clearEntityEntity;
            }

            if (entity == null)
                return;

            String message = entity.getMessage();
            ServerUtil.getOnlinePlayers().forEach(player -> player.sendMessage(new VariableFactory(message)
                    .prefix(FileResolveLag.SETTING_PREFIX)
                    .date(FileResolveLag.SETTING_DATEFORMAT)
                    .amount(count)
                    .entities(entitiesCount)
                    .chunks(chunksCount)
                    .player(player)
                    .placeholder(player)
                    .color()
                    .toString()));
            this.time = FileResolveLag.RESOLVE_LAG_SECOND_TIME;
            this.time--;
            this.chunkTime++;
        };
    }

    public int clearEntity() {
        AtomicInteger count = new AtomicInteger();
        Bukkit.getServer().getWorlds().forEach(world -> {
            // 白名单世界
            for (String s : FileResolveLag.RESOLVE_LAG_WHITE_WORLD) {
                if (!s.equalsIgnoreCase(world.getName()))
                    continue;

                return;
            }

            // 判断是否达到上限值
            if (FileResolveLag.RESOLVE_LAG_ENTITY_TOTAL_ENABLE && world.getEntities().size() < FileResolveLag.RESOLVE_LAG_ENTITY_TOTAL_LIMIT)
                return;

            world.getEntities().forEach(entity -> {
                if (entity instanceof Item) {
                    Item item = (Item) entity;
                    // 判断是否为特殊物品
                    for (String s : FileResolveLag.RESOLVE_LAG_ENTITY_SPECIAL_ITEM) {
                        if (!item.getItemStack().getType().getKey().toString().equalsIgnoreCase(s))
                            continue;

                        return;
                    }

                    count.addAndGet(item.getItemStack().getAmount());
                    entity.remove();
                    return;
                }

                if (entity instanceof LivingEntity) {
                    // 排除玩家实体
                    if (entity instanceof HumanEntity)
                        return;

                    // 判断是否为特殊实体
                    for (String s : FileResolveLag.RESOLVE_LAG_ENTITY_SPECIAL_ENTITY) {
                        try {
                            if (!entity.getType().getKey().toString().equalsIgnoreCase(s))
                                continue;
                        } catch (Exception e) {
                            if (!entity.getType().toString().equalsIgnoreCase(s))
                                continue;
                        }


                        return;
                    }

                    // 特殊动作
                    if (!FileResolveLag.RESOLVE_LAG_ENTITY_SPECIAL_POSE && entity.getPose() != Pose.STANDING)
                        return;

                    // 自定义名称
                    if (!FileResolveLag.RESOLVE_LAG_ENTITY_NAMED && entity.getCustomName() != null)
                        return;

                    // 是否为宠物
                    if (!FileResolveLag.RESOLVE_LAG_ENTITY_PET && entity instanceof Tameable) {
                        Tameable tameable = (Tameable) entity;

                        if (tameable.isTamed())
                            return;
                    }

                    count.addAndGet(1);
                    entity.remove();
                }
            });
        });
        return count.get();
    }

    public int clearChunk() {
        if (!FileResolveLag.RESOLVE_LAG_CHUNK_ENABLE)
            return 0;

        AtomicInteger count = new AtomicInteger();
        Bukkit.getServer().getWorlds().forEach(world -> {
            // 白名单世界
            for (String s : FileResolveLag.RESOLVE_LAG_WHITE_WORLD) {
                if (!s.equalsIgnoreCase(world.getName()))
                    continue;

                return;
            }

            for (Chunk loadedChunk : world.getLoadedChunks()) {
                world.unloadChunkRequest(loadedChunk.getX(), loadedChunk.getZ());
                count.addAndGet(1);
            }
        });

        return count.get();
    }

    public boolean compareInteger(int num, int num2, String operator) {
        switch (operator) {
            case "=":
                return num == num2;
            case ">":
                return num > num2;
            case "<":
                return num < num2;
            case ">=":
                return num >= num2;
            case "=>":
                return num >= num2;
            case "<=":
                return num <= num2;
            case "=<":
                return num <= num2;
        }
        return false;
    }
}