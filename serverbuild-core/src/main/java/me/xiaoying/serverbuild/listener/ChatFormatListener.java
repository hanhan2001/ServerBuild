package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.ChatFormatEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.tables.ChatFormatMuteTable;
import me.xiaoying.serverbuild.utils.DateUtil;
import me.xiaoying.serverbuild.utils.PlayerUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import me.xiaoying.sqlfactory.sentence.Create;
import me.xiaoying.sqlfactory.sentence.Delete;
import me.xiaoying.sqlfactory.sentence.Select;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener ChatFormat
 */
public class ChatFormatListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setCancelled(true);

        // mute
        // 此处代码还需更改，暂时性支持新版本 SqlFactory
        // 理论上讲查询到的信息存入缓存中才是最好的操作
        SBPlugin.getSqlFactory().run(new Create(ChatFormatMuteTable.class));
        Object object = SBPlugin.getSqlFactory().run(new Select(ChatFormatMuteTable.class).where("uuid", player.getUniqueId().toString()));

        if (object != null) {
            ChatFormatMuteTable table = (ChatFormatMuteTable) object;

            if (table.overdue())
                SBPlugin.getSqlFactory().run(new Delete(table));
            else {
                player.sendMessage(new VariableFactory(FileChatFormat.MUTE_MESSAGE)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .time(table.getDistance().getTime() / 1000)
                        .color()
                        .toString());
                return;
            }
        }

        // color
        if (event.getMessage().contains("&") && (!player.isOp() && !PlayerUtil.hasPermission(player, "sb.admin", "sb.cf.admin", "sb.cf.color"))) {
            player.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_MISSING_PERMISSION)
                    .date(FileChatFormat.SETTING_DATEFORMAT)
                    .prefix(FileChatFormat.SETTING_PREFIX)
                    .player(player)
                    .placeholder(player)
                    .color()
                    .toString());
            return;
        }

        // character limit
        if (FileChatFormat.CHARACTER_LIMIT_ENABLE & message.length() > FileChatFormat.CHARACTER_LIMIT_LIMIT) {
            player.sendMessage(new VariableFactory(FileChatFormat.CHARACTER_LIMIT_MESSAGE)
                            .date(FileChatFormat.SETTING_DATEFORMAT)
                            .prefix(FileChatFormat.SETTING_PREFIX)
                            .player(player)
                            .placeholder(player)
                            .color()
                            .toString());
            return;
        }

        boolean send = true;
        // black terms
        if (FileChatFormat.BLACK_TERMS_ENABLE) {
            for (String string : FileChatFormat.BLACK_TERMS_TERMS) {
                if (!StringUtil.match(string, message, 0, 0) && !message.contains(string))
                    continue;

                if (FileChatFormat.BLACK_TERMS_CANCEL)
                    send = false;

                if (FileChatFormat.BLACK_TERMS_FOR_EVERY_BODY) {
                    player.sendMessage(new VariableFactory(FileChatFormat.BLACK_TERMS_MESSAGE)
                                    .prefix(FileChatFormat.SETTING_PREFIX)
                                    .player(player)
                                    .date(FileChatFormat.SETTING_DATEFORMAT)
                                    .placeholder(player)
                                    .color()
                                    .toString());
                    String[] split = FileChatFormat.BLACK_TERMS_SCRIPT.split("\n");
                    for (String s : split)
                        SBPlugin.getScriptManager().performScript(s, player);
                    break;
                }

                if (player.isOp() || PlayerUtil.hasPermission(player, "sb.admin", "sb.cf.admin", "sb.cf.jump")) {
                    send = true;
                    break;
                }

                player.sendMessage(new VariableFactory(FileChatFormat.BLACK_TERMS_MESSAGE)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .player(player)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .placeholder(player)
                        .color()
                        .toString());
                String[] split = FileChatFormat.BLACK_TERMS_SCRIPT.split("\n");
                for (String s : split)
                    SBPlugin.getScriptManager().performScript(s, player);
            }
        }

        // get ChatFormatEntity
        ChatFormatModule module = (ChatFormatModule) SBPlugin.getModuleManager().getModule("ChatFormat");
        ChatFormatEntity entity = null;
        for (ChatFormatEntity chatFormatEntity : module.getChatFormatEntities()) {
            if (!player.hasPermission(chatFormatEntity.getPermission()))
                continue;

            if (entity == null) {
                entity = chatFormatEntity;
                continue;
            }

            if (entity.getPriority() < chatFormatEntity.getPriority())
                continue;

            entity = chatFormatEntity;
        }

        // handle entity
        if (entity != null) {
            String format = entity.getFormat();
            format = new VariableFactory(format).message(message).toString();
            message = format;
        }
        message = new VariableFactory(message)
                .date(FileChatFormat.SETTING_DATEFORMAT)
                .prefix(FileChatFormat.SETTING_PREFIX)
                .player(player)
                .placeholder(player)
                .color()
                .toString();

        // match @
        if (FileChatFormat.CALL_ENABLE) {
            List<Player> atPlayers = this.at(message);
            for (Player atPlayer : atPlayers) {
                message = new VariableFactory(message.replace(FileChatFormat.CALL_KEY + atPlayer.getName(), "&b" + FileChatFormat.CALL_KEY + atPlayer.getName() + "&f")).color().toString();
                atPlayer.playSound(atPlayer.getLocation(), FileChatFormat.CALL_SOUND, 1F, 0F);
            }
        }

        if (!send)
            return;

        TextComponent textComponent = null;
        item: if (message.contains("[item]")) {
            textComponent = new TextComponent();
            if (player.getItemInUse() == null || player.getItemInUse().getType() == Material.AIR)
                break item;

            String[] split = message.split("\\[itme]");
            for (int i = 0; i < split.length; i++) {
                textComponent.addExtra(split[i]);

                if (i == split.length - 1)
                    break;

                TextComponent textComponent1 = new TextComponent(player.getItemInUse().getItemMeta().getDisplayName());
                textComponent1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM));
                textComponent.addExtra(textComponent1);
            }

            if (message.endsWith("[item]")) {
                TextComponent textComponent1 = new TextComponent(player.getItemInUse().getItemMeta().getDisplayName());
                textComponent1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM));
                textComponent.addExtra(textComponent1);
            }
        }

        if (textComponent == null) {
            try {
                for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
                    onlinePlayer.sendMessage(player.getUniqueId(), message);
            } catch (Exception e) {
                for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
                    onlinePlayer.sendMessage(message);
            }

            ServerUtil.sendMessage(message);
            return;
        }

        for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
            onlinePlayer.spigot().sendMessage(textComponent);

        ServerUtil.sendMessage(textComponent.getText());
    }

    private List<Player> at(String message) {
        if (!message.contains(FileChatFormat.CALL_KEY))
            return new ArrayList<>();

        Player player = null;
        boolean match = false;
        List<Player> players = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : message.split("")) {
            if (s.equalsIgnoreCase(FileChatFormat.CALL_KEY)) {
                if (stringBuilder.toString().length() != 0) {
                    players.add(player);
                    stringBuilder = new StringBuilder();
                }
                match = true;
                continue;
            }

            // 是否处于匹配模式
            if (!match)
                continue;

            stringBuilder.append(s);
            Player p;
            if ((p = Bukkit.getServer().getPlayerExact(stringBuilder.toString())) != null)
                player = p;
        }
        if (player != null)
            players.add(player);

        return players;
    }
}