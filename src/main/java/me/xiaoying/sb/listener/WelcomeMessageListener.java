package me.xiaoying.sb.listener;

import me.xiaoying.sb.constant.WelcomeMessageConstant;
import me.xiaoying.sb.entity.WelcomeMessageEntity;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.service.WelcomeMessageService;
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

        Player player = event.getPlayer();
        WelcomeMessageEntity welcomeMessageEntity = null;
        for (WelcomeMessageEntity messageEntity : WelcomeMessageService.getWelcomeMessageEntities()) {
            if (!player.hasPermission(messageEntity.getPermission()))
                continue;

            if (welcomeMessageEntity == null) {
                welcomeMessageEntity = messageEntity;
                continue;
            }
            if (welcomeMessageEntity.getPriority() > messageEntity.getPriority())
                welcomeMessageEntity = messageEntity;
        }

        if (welcomeMessageEntity == null)
            return;

        event.setJoinMessage(null);

        // Chat
        if (welcomeMessageEntity.enableJoinChat())
            welcomeMessageEntity.getJoinChatMessage().forEach(s -> ServerUtil.onlinePlayersSendMessage(new VariableFactory(s).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).getString()));

        //  Title
        if (welcomeMessageEntity.enableJoinTitle()) {
            String title = welcomeMessageEntity.getJoinTitleTitle();
            if (title != null)
                title = new VariableFactory(title).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).getString();


            String subtitle = welcomeMessageEntity.getJoinTitleSubtitle();
            if (subtitle != null)
                subtitle = new VariableFactory(subtitle).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).getString();

            if (welcomeMessageEntity.enableJoinTitleAllPlayer())
                ServerUtil.onlinePlayersSendTitle(title, subtitle);
            else
                PlayerUtil.sendTitle(player, title, subtitle);
        }

        // Actionbar
        if (welcomeMessageEntity.enableJoinActionBar()) {
            if (welcomeMessageEntity.enableJoinActionbarAllPlayer())
                ServerUtil.onlinePlayersSendActionbar(new VariableFactory(welcomeMessageEntity.getJoinActionbarMessage()).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).getString());
            else
                PlayerUtil.sendActionbar(player, new VariableFactory(welcomeMessageEntity.getJoinActionbarMessage()).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).getString());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Handle handle = Handler.getHandle("WelcomeMessage");
        if (!handle.enable())
            return;

        List<WelcomeMessageEntity> list = new ArrayList<>();

        Player player = event.getPlayer();
        WelcomeMessageService.getWelcomeMessageEntities().forEach(entity -> {
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
            welcomeMessageEntity.getQuitChatMessage().forEach(s -> ServerUtil.onlinePlayersSendMessage(new VariableFactory(s).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).toString()));

        //  Title
        if (welcomeMessageEntity.enableQuitTitle()) {
            String title = welcomeMessageEntity.getQuitTitleTitle();
            if (title != null)
                title = new VariableFactory(title).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).getString();

            String subtitle = welcomeMessageEntity.getQuitTitleSubtitle();
            if (subtitle != null)
                subtitle = new VariableFactory(subtitle).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).getString();

            ServerUtil.onlinePlayersSendTitle(title, subtitle);
        }

        // Actionbar
        if (welcomeMessageEntity.enableQuitActionBar())
                ServerUtil.onlinePlayersSendActionbar(new VariableFactory(welcomeMessageEntity.getQuitActionbarMessage()).date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT).toString());
    }
}