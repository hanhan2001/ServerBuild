package me.xiaoying.sb.listener;

import me.xiaoying.sb.files.config.FileLoginTp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 监听事件 LoginTP
 */
public class LoginTPListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!FileLoginTp.SET_ENABLE)
            return;

        Player player = event.getPlayer();
        Location location = new Location(FileLoginTp.LOCATION_WORLD, FileLoginTp.LOCATION_X, FileLoginTp.LOCATION_Y, FileLoginTp.LOCATION_Z, FileLoginTp.LOCATION_YAW, FileLoginTp.LOCATION_PITCH);
        player.teleport(location);
    }
}