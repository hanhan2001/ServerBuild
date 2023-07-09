package me.xiaoying.sb.listener.listeners;

import me.xiaoying.sb.constant.NotBuildConstant;
import me.xiaoying.sb.utils.ColorUtil;
import me.xiaoying.sb.utils.DateUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

/**
 * 事件监听 NotBuild
 */
public class NotBuildListener implements Listener {
    @EventHandler
    public void onPlayerBuild(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (!NotBuildConstant.BUILD_WORLDS.contains(Objects.requireNonNull(location.getWorld()).getName()))
            return;

        if (player.hasPermission("sb.nb.destruction") || !player.isOp())
            return;

        event.setCancelled(true);
        NotBuildConstant.MESSAGE_BUILD.forEach(string -> {
            if (string.contains("%date%"))
                string = string.replace("%date%", DateUtil.getDate(NotBuildConstant.SET_VARIABLE_DATEFORMAT));
            player.sendMessage(ColorUtil.translate(string));
        });
    }

    @EventHandler
    public void onPlayerDestruction(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (!NotBuildConstant.DESTRUCTION_WORLDS.contains(Objects.requireNonNull(location.getWorld()).getName()))
            return;

        if (player.hasPermission("sb.nb.destruction") || !player.isOp())
            return;

        event.setCancelled(true);
        NotBuildConstant.MESSAGE_DESTRUCTION.forEach(string -> {
            if (string.contains("%date%"))
                string = string.replace("%date%", DateUtil.getDate(NotBuildConstant.SET_VARIABLE_DATEFORMAT));
            player.sendMessage(ColorUtil.translate(string));
        });
    }
}