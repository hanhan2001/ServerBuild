package me.xiaoying.sb.listener.listeners;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.entity.ChatFormatEntity;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.ChatFormatService;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 监听事件 ChatFormat
 */
public class ChatFormatListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChatMute(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.getMetadata("mute").size() == 0)
            return;

        event.setCancelled(true);
        for (String s : ChatFormatConstant.CHAT_MUTE_MESSAGE) {
            player.sendMessage(new VariableFactory(s)
                    .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                    .player(player)
                    .time(player.getMetadata("mute").get(0).asString())
                    .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                    .placeholder(player)
                    .color()
                    .getString());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChatBlackTerms(AsyncPlayerChatEvent event) {
        if (!ChatFormatConstant.BLACK_TERMS_ENABLE)
            return;

        String result = blackTerms(event);
        if (result != null)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChatCharaLimit(AsyncPlayerChatEvent event) {
        if (event.getPlayer().hasPermission("sb.cf.jump")
                || event.getPlayer().hasPermission("sb.cf.admin")
                || event.getPlayer().isOp())
            return;

        if (!ChatFormatConstant.CHAR_LIMIT_ENABLE || event.getMessage().length() <= ChatFormatConstant.CHAR_LIMIT_LIMIT)
            return;

        for (String s : ChatFormatConstant.CHAR_LIMIT_MESSAGE) {
            s = new VariableFactory(s)
                    .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                    .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                    .placeholder(event.getPlayer())
                    .color().getString();
            event.getPlayer().sendMessage(s);
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;

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

        // 如果没有获取到 Format 则按原来内容显示
        if (chatFormat == null)
            return;

        // call
        for (Player callPlayer : getCallPlayers(event.getMessage()))
            event.setMessage(event.getMessage().replace(ChatFormatConstant.CALL_KEY + callPlayer.getName(), "&b" + ChatFormatConstant.CALL_KEY + callPlayer.getName() + "&r"));

        List<Player> callPlayer = getCallPlayers(event.getMessage());

        // 发送消息
        for (Player onlinePlayer : ServerUtil.getOnlinePlayers()) {
            if (callPlayer.contains(onlinePlayer))
                onlinePlayer.playSound(onlinePlayer.getLocation(), ChatFormatConstant.CALL_SOUND, 1F, 0F);
            chatFormat.getFormat().forEach(string -> {
                string = new VariableFactory(string)
                        .player(player)
                        .chatmessgae(event.getMessage())
                        .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                        .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                        .placeholder(onlinePlayer)
                        .color()
                        .getString();
                onlinePlayer.sendMessage(string);
                ServerUtil.sendMessage(string, true);
            });
        }
        event.setCancelled(true);
    }

    /**
     * 获取字符串内的玩家
     *
     * @param str 字符串
     * @return ArrayList
     */
    private List<Player> getCallPlayers(String str) {
        List<Player> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        boolean isRegex = false;
        boolean isAdd = false;
        int index = 0;
        for (String s : str.split("")) {
            if (s.equalsIgnoreCase(ChatFormatConstant.CALL_KEY) && !isRegex) {
                isRegex = true;
                continue;
            }

            if (s.equalsIgnoreCase(ChatFormatConstant.CALL_KEY) && isRegex) {
                isRegex = false;
                isAdd = false;
                stringBuilder.delete(0, stringBuilder.length());
                continue;
            }

            if (isRegex)
                stringBuilder.append(s);

            if (Bukkit.getPlayerExact(stringBuilder.toString()) != null && !isAdd) {
                list.add(Bukkit.getPlayerExact(stringBuilder.toString()));
                isAdd = true;
                index++;
                continue;
            }

            if (Bukkit.getPlayerExact(stringBuilder.toString()) != null && isAdd) {
                list.remove(index);
                list.add(Bukkit.getPlayerExact(stringBuilder.toString()));
            }
        }

        return list;
    }

    private String blackTerms(AsyncPlayerChatEvent event) {
        if (!ChatFormatConstant.BLACK_TERMS_EVERY
                && (event.getPlayer().hasPermission("sb.cf.jump")
                    || event.getPlayer().hasPermission("sb.cf.admin")
                    || event.getPlayer().isOp()))
            return null;

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

        // 执行 to-do 内命令
        for (String cmd : ChatFormatConstant.BLACK_TERMS_TODO)
            ServerBuild.getScriptCommandService().onCommand(cmd, player);

        // 发送Message
        for (String s : ChatFormatConstant.BLACK_TERMS_MESSAGE) {
            player.sendMessage(new VariableFactory(s)
                            .player(player)
                            .chatmessgae(event.getMessage())
                            .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                            .placeholder(player)
                            .color()
                            .getString());
        }
        if (ChatFormatConstant.BLACK_TERMS_CANCEL)
            return "return";

        return null;
    }
}