package me.xiaoying.sb.listener;

import me.xiaoying.sb.files.config.FileNotBuild;
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
        if (!FileNotBuild.SET_ENABLE || !FileNotBuild.SET_BUILD_ENABLE)
            return;

        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (!FileNotBuild.BUILD_WORLDS.contains(Objects.requireNonNull(location.getWorld()).getName()))
            return;

        if (player.hasPermission("sb.nb.destruction") || !player.isOp())
            return;

        event.setCancelled(true);
        FileNotBuild.MESSAGE_BUILD.forEach(string -> {
            if (string.contains("%date%"))
                string = string.replace("%date%", DateUtil.getDate(FileNotBuild.SET_DATEFORMAT));
            player.sendMessage(ColorUtil.translate(string));
        });
    }

    @EventHandler
    public void onPlayerDestruction(BlockBreakEvent event) {
        if (!FileNotBuild.SET_ENABLE || !FileNotBuild.SET_DESTRUCTION_ENABLE)
            return;

        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (!FileNotBuild.DESTRUCTION_WORLDS.contains(Objects.requireNonNull(location.getWorld()).getName()))
            return;

        if (player.hasPermission("sb.nb.destruction") || !player.isOp())
            return;

        event.setCancelled(true);
        FileNotBuild.MESSAGE_DESTRUCTION.forEach(string -> {
            if (string.contains("%date%"))
                string = string.replace("%date%", DateUtil.getDate(FileNotBuild.SET_DATEFORMAT));
            player.sendMessage(ColorUtil.translate(string));
        });
    }
}