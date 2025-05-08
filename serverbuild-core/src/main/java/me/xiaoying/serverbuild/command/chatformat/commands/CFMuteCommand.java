package me.xiaoying.serverbuild.command.chatformat.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.tables.ChatFormatMuteTable;
import me.xiaoying.serverbuild.utils.DateUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.sqlfactory.sentence.Create;
import me.xiaoying.sqlfactory.sentence.Delete;
import me.xiaoying.sqlfactory.sentence.Insert;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Command(values = "mute", length = {1, 2})
public class CFMuteCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileChatFormat.MESSAGE_HELP)
                .prefix(FileChatFormat.SETTING_PREFIX)
                .date(FileChatFormat.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.cf.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_MISSING_PERMISSION)
                            .prefix(FileChatFormat.SETTING_PREFIX)
                            .date(FileChatFormat.SETTING_DATEFORMAT)
                            .color()
                            .toString());
            return;
        }

        // create tables
        SBPlugin.getSqlFactory().run(new Create(ChatFormatMuteTable.class));

        Player player = Bukkit.getServer().getPlayerExact(strings[0]);
        String save = DateUtil.getDate(FileChatFormat.SETTING_DATEFORMAT);
        Date date;
        String over;
        if (strings.length == 1)
            date = DateUtil.translate(FileChatFormat.MUTE_DEFAULT_TIME, Date.class);
        else
            date = DateUtil.translate(strings[1], Date.class);

        if (date == null) {
            sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_MUTE_WRONG)
                            .prefix(FileChatFormat.SETTING_PREFIX)
                            .date(FileChatFormat.SETTING_DATEFORMAT)
                            .color()
                            .toString());
            return;
        }

        over = DateUtil.getDate(DateUtil.getDate(date, FileChatFormat.SETTING_DATEFORMAT));

        if (player == null) {
            sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_NOT_FOUND_PLAYER)
                    .prefix(FileChatFormat.SETTING_PREFIX)
                    .date(FileChatFormat.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        SBPlugin.getSqlFactory().run(new Delete(ChatFormatMuteTable.class).where("uuid", player.getUniqueId().toString()));
        SBPlugin.getSqlFactory().run(new Insert().insert(player.getUniqueId().toString(), save, over));

        // calculate time
        long lastTime = DateUtil.getDateReduce(over, save, FileChatFormat.SETTING_DATEFORMAT) / 1000;
        player.sendMessage(new VariableFactory(FileChatFormat.MUTE_MESSAGE)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .time(lastTime)
                        .color()
                        .toString());

        sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_MUTE_SUCCESS)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .time(lastTime)
                        .color()
                        .toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1)
            ServerUtil.getOnlinePlayers().forEach(player -> list.add(player.getName()));
        else if (strings.length == 2)
            list.add("10s");

        List<String> conditionList = new ArrayList<>();
        for (String s : list) {
            if (!s.toUpperCase(Locale.ENGLISH).startsWith(strings[strings.length - 1].toUpperCase(Locale.ENGLISH)))
                continue;

            conditionList.add(s);
        }
        return conditionList;
    }
}