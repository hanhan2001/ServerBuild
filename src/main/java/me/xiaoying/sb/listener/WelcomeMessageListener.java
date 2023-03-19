package me.xiaoying.sb.listener;

import me.xiaoying.sb.cache.Caches;
import me.xiaoying.sb.entity.WelcomeMessageEntity;
import me.xiaoying.sb.files.config.FileWelcomeMessage;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.DateUtil;
import me.xiaoying.sb.utils.PlayerUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件监听 WelcomeMessage
 */
public class WelcomeMessageListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Handle handle = Handler.getHandle("WelcomeMessage");
        if (!handle.enable())
            return;

        List<WelcomeMessageEntity> list = new ArrayList<>();

        Player player = event.getPlayer();
        Caches.welcomeMessageEntities.forEach(entity -> {
            if (!player.hasPermission(entity.getPermission()))
                return;
            list.add(entity);
        });

        if (list.size() == 0)
            return;

        event.setJoinMessage(null);

        WelcomeMessageEntity welcomeMessageEntity = null;
        for (WelcomeMessageEntity messageEntity : list) {
            if (welcomeMessageEntity == null)
                welcomeMessageEntity = messageEntity;

            if (welcomeMessageEntity.getPriority() < messageEntity.getPriority())
                welcomeMessageEntity = messageEntity;
        }

        // Chat
        assert welcomeMessageEntity != null;
        if (welcomeMessageEntity.enableJoinChat())
            welcomeMessageEntity.getJoinChatMessage().forEach(s -> {
                s = s.replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT));
                ServerUtil.onlinePlayersSendMessage(s);
            });

        //  Title
        if (welcomeMessageEntity.enableJoinTitle()) {
            String title = welcomeMessageEntity.getJoinTitleTitle();
            title = title.replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT));
            String subtitle = welcomeMessageEntity.getJoinTitleSubtitle();
            subtitle = subtitle.replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT));

            if (welcomeMessageEntity.enableJoinTitleAllPlayer())
                ServerUtil.onlinePlayersSendTitle(title, subtitle);
            else
                PlayerUtil.sendTitle(player, title, subtitle);
        }

        // Actionbar
        if (welcomeMessageEntity.enableJoinActionBar()) {
            if (welcomeMessageEntity.enableJoinActionbarAllPlayer())
                ServerUtil.onlinePlayersSendActionbar(welcomeMessageEntity.getJoinActionbarMessage().replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT)));
            else
                PlayerUtil.sendActionbar(player, welcomeMessageEntity.getJoinActionbarMessage().replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT)));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Handle handle = Handler.getHandle("WelcomeMessage");
        if (!handle.enable())
            return;

        List<WelcomeMessageEntity> list = new ArrayList<>();

        Player player = event.getPlayer();
        Caches.welcomeMessageEntities.forEach(entity -> {
            if (!player.hasPermission(entity.getPermission()))
                return;
            list.add(entity);
        });

        if (list.size() == 0)
            return;

        event.setQuitMessage(null);

        WelcomeMessageEntity welcomeMessageEntity = null;
        for (WelcomeMessageEntity messageEntity : list) {
            if (welcomeMessageEntity == null)
                welcomeMessageEntity = messageEntity;

            if (welcomeMessageEntity.getPriority() < messageEntity.getPriority())
                welcomeMessageEntity = messageEntity;
        }

        // Chat
        assert welcomeMessageEntity != null;
        if (welcomeMessageEntity.enableQuitChat())
            welcomeMessageEntity.getQuitChatMessage().forEach(s -> {
                s = s.replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT));
                ServerUtil.onlinePlayersSendMessage(s);
            });

        //  Title
        if (welcomeMessageEntity.enableQuitTitle()) {
            String title = welcomeMessageEntity.getQuitTitleTitle();
            title = title.replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT));
            String subtitle = welcomeMessageEntity.getQuitTitleSubtitle();
            subtitle = subtitle.replace("%date", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT));

            ServerUtil.onlinePlayersSendTitle(title, subtitle);
        }

        // Actionbar
        if (welcomeMessageEntity.enableQuitActionBar())
                ServerUtil.onlinePlayersSendActionbar(welcomeMessageEntity.getQuitActionbarMessage().replace("%date%", DateUtil.getDate(FileWelcomeMessage.SET_VARIABLE_DATEFORMAT)));
    }
}