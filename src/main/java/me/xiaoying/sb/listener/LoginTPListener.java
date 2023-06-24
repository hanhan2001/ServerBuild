package me.xiaoying.sb.listener;

import me.xiaoying.sb.constant.LoginTpConstant;
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
        if (!LoginTpConstant.SET_ENABLE)
            return;

        Player player = event.getPlayer();
        Location location = new Location(LoginTpConstant.LOCATION_WORLD, LoginTpConstant.LOCATION_X, LoginTpConstant.LOCATION_Y, LoginTpConstant.LOCATION_Z, LoginTpConstant.LOCATION_YAW, LoginTpConstant.LOCATION_PITCH);
        player.teleport(location);
    }
}