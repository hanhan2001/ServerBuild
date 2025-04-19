package me.xiaoying.serverbuild.command.resolvelag.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagMemoryEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagTPSEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.*;

@Command(values = "gc", length = 0, permission = {"sb.admin", "sb.rl.admin", "sb.rl.gc"})
public class RLGcCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileResolveLag.MESSAGE_HELP)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!sender.isOp() && !ServerUtil.hasPermission(sender, "sb.admin", "sb.rl.admin", "sb.rl.gc")) {
            sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_MISSING_PERMISSION).prefix(FileResolveLag.SETTING_PREFIX).date(FileResolveLag.SETTING_PREFIX).color().toString());
            return;
        }

        VariableFactory variableFactory = new VariableFactory(FileResolveLag.MESSAGE_STATE)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .server_version(Bukkit.getServer().getVersion())
                .server_bukkit_version(Bukkit.getServer().getBukkitVersion())
                .server_whitelist_enabled(Bukkit.getServer().hasWhitelist())
                .server_memory_max((Runtime.getRuntime().maxMemory() / (1024 * 1024)) + " MB");

        // tps
        variableFactory = this.handleTPS(this.handleTPS(variableFactory));

        // memory
        variableFactory = this.handleMemory(variableFactory);

        StringBuilder worlds_status = new StringBuilder();
        // worlds
        for (int i = 0; i < Bukkit.getServer().getWorlds().size(); i++) {
            World world = Bukkit.getServer().getWorlds().get(i);

            int tileEntities = 0;
            for (Chunk loadedChunk : world.getLoadedChunks())
                tileEntities += loadedChunk.getTileEntities().length;

            worlds_status.append(new VariableFactory(FileResolveLag.RESOLVELAG_STATE_WORLD)
                    .world(world)
                    .entities(world.getEntities().size())
                    .chunks(world.getLoadedChunks().length)
                    .server_world_tile_block(tileEntities)
                    .toString());
        }

        variableFactory = variableFactory.server_world_status(worlds_status.toString());

        sender.sendMessage(variableFactory.placeholder(sender)
                .color()
                .toString());
    }

    public VariableFactory handleTPS(VariableFactory variableFactory) {
        ResolveLagModule module = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");

        double[] tps = module.getTPS();
        Map<Integer, ResolveLagTPSEntity> entities = new HashMap<>();

        for (ResolveLagTPSEntity tpsEntity : module.getTpsManager().getEntities()) {
            for (int i = 0; i < tps.length; i++) {
                if (!tpsEntity.match(tps[i]))
                    continue;

                if (entities.get(i) == null) {
                    entities.put(i, tpsEntity);
                    continue;
                }

                if (entities.get(i).getCount() <= tpsEntity.getCount())
                    continue;

                entities.put(i, tpsEntity);
            }
        }

        for (int i = 0; i < tps.length; i++) {
            String color = entities.get(i) == null ? "" : entities.get(i).getColor();
            if (i == 0)
                variableFactory = variableFactory.server_tps_1m(color + tps[i]);
            else if (i == 1)
                variableFactory = variableFactory.server_tps_5m(color + tps[i]);
            else
                variableFactory = variableFactory.server_tps_15m(color + tps[i]);
        }

        return variableFactory;
    }

    public VariableFactory handleMemory(VariableFactory variableFactory) {
        ResolveLagModule module = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");

        long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);

        // now
        long now = (Runtime.getRuntime().totalMemory() / (1024 * 1024));
        int nowPercentage = (int) ((double) now / (double) max * 100);

        ResolveLagMemoryEntity nowEntity = null;
        for (ResolveLagMemoryEntity entity : module.getMemoryManager().getNowEntities()) {
            if (!entity.match(nowPercentage))
                continue;

            if (nowEntity == null) {
                nowEntity = entity;
                continue;
            }

            if (nowEntity.getCount() >= entity.getCount())
                continue;

            nowEntity = entity;
        }

        if (nowEntity == null)
            variableFactory = variableFactory.server_memory_now(now + " MB");
        else
            variableFactory = variableFactory.server_memory_now(nowEntity.getColor() + now + " MB");

        // idle
        long idle = ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) / (1024 * 1024));
        int idlePercentage = (int) ((double) idle / (double) max * 100);

        ResolveLagMemoryEntity idleEntity = null;
        for (ResolveLagMemoryEntity entity : module.getMemoryManager().getIdleEntities()) {
            if (!entity.match(idlePercentage))
                continue;

            if (idleEntity == null) {
                idleEntity = entity;
                continue;
            }

            if (idleEntity.getCount() <= entity.getCount())
                continue;

            idleEntity = entity;
        }

        if (idleEntity == null)
            variableFactory = variableFactory.server_memory_idle(idle + " MB");
        else
            variableFactory = variableFactory.server_memory_idle(idleEntity.getColor() + idle + " MB");

        return variableFactory;
    }
}