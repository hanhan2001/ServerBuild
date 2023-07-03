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

        if (chatFormat == null)
            return;

        for (Player callPlayer : getCallPlayers(event.getMessage()))
            event.setMessage(event.getMessage().replace(ChatFormatConstant.CALL_KEY + callPlayer.getName(), "&b" + ChatFormatConstant.CALL_KEY + callPlayer.getName() + "&r"));

        List<Player> callPlayer = getCallPlayers(event.getMessage());

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
                System.out.println(getCallPlayers(string));
                onlinePlayer.sendMessage(string);
                ServerUtil.sendMessage(string, true);
            });
        }
        event.setCancelled(true);
    }

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
            System.out.println(stringBuilder.toString());
        }

        return list;
    }

    private String blackTerms(AsyncPlayerChatEvent event) {
        if (!ChatFormatConstant.BLACK_TERMS_EVERY && (event.getPlayer().hasPermission("sb.cf.jump") || event.getPlayer().isOp()))
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

        todo(player);
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

    private void todo(Player player) {
        if (!ChatFormatConstant.BLACK_TERMS_EVERY && (player.hasPermission("sb.cf.jump") || player.isOp()))
            return;

        for (String cmd : ChatFormatConstant.BLACK_TERMS_TODO) {
            ServerBuild.getScriptCommandService().onCommand(cmd, player);
        }
    }
}