package me.xiaoying.sb.listener.listeners;

import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.entity.ChatFormatEntity;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.service.ChatFormatService;
import me.xiaoying.sb.utils.PlayerUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * 监听事件 ChatFormat
 */
public class ChatFormatListener implements Listener {
    Handle handle = Handler.getHandle("ChatFormat");

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!handle.enable())
            return;

        // 屏蔽关键词
        if (ChatFormatConstant.BLACK_TERMS_ENABLE) {
            if (blackTerms(event).equalsIgnoreCase("return"))
                return;
        }

        // 数量限制
        if (ChatFormatConstant.CHAR_LIMIT_ENABLE && event.getMessage().length() > ChatFormatConstant.CHAR_LIMIT_LIMIT) {
            for (String s : ChatFormatConstant.CHAR_LIMIT_MESSAGE) {
                s = new VariableFactory(s)
                        .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                        .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                        .placeholder(event.getPlayer())
                        .color().getString();
                event.getPlayer().sendMessage(s);
            }
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();

        // 聊天格式
        ChatFormatEntity chatFormat = null;
        int priority = 0;
        for (ChatFormatEntity chatFormatEntity : ChatFormatService.getChatFormatEntities()) {
            if (!player.hasPermission(chatFormatEntity.getPermission()) && !player.isOp())
                continue;

            if (chatFormat == null) {
                chatFormat = chatFormatEntity;
                priority = chatFormatEntity.getPriority();
                continue;
            }

            if (priority >= chatFormatEntity.getPriority())
                chatFormat = chatFormatEntity;
        }

        for (String s : chatFormat.getFormat()) {
            s = new VariableFactory(s)
                    .player(player)
                    .chatmessgae(event.getMessage())
                    .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                    .getString();
            ServerUtil.onlinePlayersSendMessage(s);
            ServerUtil.sendMessage(s, true);
        }
        event.setCancelled(true);
    }

    private String blackTerms(AsyncPlayerChatEvent event) {
        boolean hasTerm = false;
        for (String blackTermsTerm : ChatFormatConstant.BLACK_TERMS_TERMS) {
            if (event.getMessage().contains(blackTermsTerm)) {
                hasTerm = true;
                break;
            }
        }

        if (!hasTerm)
            return null;

        Player player = event.getPlayer();

        todo(player);

        if (ChatFormatConstant.BLACK_TERMS_CANCEL)
            return "return";

        return null;
    }

    private void todo(Player player) {
        if (!ChatFormatConstant.BLACK_TERMS_EVERY && (player.hasPermission("sb.cf.jump") || player.isOp()))
            return;

        for (String cmd : ChatFormatConstant.BLACK_TERMS_TODO) {
            String[] strs = cmd.split(" ");

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < strs.length; i++) stringBuilder.append(strs[i]).append(" ");
            String todo = stringBuilder.toString();
            todo = new VariableFactory(todo).placeholder(player).color().getString();
            // 定义 标头
            String howdo = strs[0];

            // 判断 格式
            switch (howdo.toUpperCase()) {
                case "COMMAND:":
                    player.performCommand(todo);
                    break;
                case "OPCOMMAND:":
                    if (player.isOp())
                        player.performCommand(todo);
                    else {
                        player.setOp(true);
                        player.performCommand(todo);
                        player.setOp(false);
                    }
                    break;
                case "SEND:":
                    PlayerUtil.sendMessage(player, todo);
                    break;
                default:
                    StringBuilder stringBuilder1 = new StringBuilder();
                    for (String str : strs)
                        stringBuilder1.append(str);
                    String howdo1 = stringBuilder1.toString();
                    howdo1 = new VariableFactory(howdo1).placeholder(player).color().getString();
                    PlayerUtil.sendMessage(player, howdo1);
            }
        }
    }
}