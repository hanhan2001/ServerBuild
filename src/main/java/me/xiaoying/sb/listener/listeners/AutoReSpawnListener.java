package me.xiaoying.sb.listener.listeners;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.AutoReSpawnConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AutoReSpawnListener implements Listener {
    Handle handle = Handler.getHandle("AutoReSpawn");

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!this.handle.enable())
            return;

        Player player = event.getEntity();
        player.spigot().respawn();
    }

    @EventHandler
    public void onPlayerReSpawn(PlayerRespawnEvent event) {
        if (!this.handle.enable())
            return;

        Player player = event.getPlayer();

        String dateFormat = AutoReSpawnConstant.SET_VARIABLE_DATEFORMAT;
        if (AutoReSpawnConstant.ENABLE_CHAT) {
            for (String str : AutoReSpawnConstant.CHAT_FORMAT)
                player.sendMessage(new VariableFactory(str).player(player).date(dateFormat).placeholder(player).color().getString());
        }

        if (AutoReSpawnConstant.ENABLE_TITLE) {
            String title = AutoReSpawnConstant.TITLE_TITLE;
            String subTitle = AutoReSpawnConstant.TITLE_SUBTITLE;
            if (title != null)
                title = new VariableFactory(title).player(player).date(dateFormat).placeholder(player).color().getString();
            if (subTitle != null)
                subTitle = new VariableFactory(subTitle).player(player).date(dateFormat).placeholder(player).color().getString();

            player.sendTitle(title, subTitle, AutoReSpawnConstant.TITLE_FADE_IN, AutoReSpawnConstant.TITLE_STAY, AutoReSpawnConstant.TITLE_FADEOUT);
        }

        if (AutoReSpawnConstant.ENABLE_ACTIONBAR) {
            String message = AutoReSpawnConstant.ACTIONBAR_MESSAGE;
            message = new VariableFactory(message).player(player).date(dateFormat).placeholder(player).color().getString();

            String finalMessage = message;
            int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(ServerBuild.getInstance(), () -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(finalMessage)), 0L, 20L);
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(AutoReSpawnConstant.ACTIONBAR_TIME);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
                Bukkit.getScheduler().cancelTask(i);
            });
        }
    }
}