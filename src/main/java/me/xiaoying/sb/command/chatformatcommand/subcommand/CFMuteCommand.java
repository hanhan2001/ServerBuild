package me.xiaoying.sb.command.chatformatcommand.subcommand;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.exception.PlayerDataException;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.playerdata.SubPlayerData;
import me.xiaoying.sb.playerdata.data.ChatFormatPlayerData;
import me.xiaoying.sb.utils.DateUtil;
import me.xiaoying.sb.utils.ExceptionUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Command(values = "mute", length = {1, 2})
public class CFMuteCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        Player player = Bukkit.getServer().getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_NOTFOUNPAYER)
                            .player(args[0])
                            .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                            .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString());
            return false;
        }

        ChatFormatPlayerData chatFormatPlayerData = null;
        String handle = "chatformat";
        String handleName = "ChatFormat_Mute";
        for (SubPlayerData chatformat : ServerBuild.getPlayerDataService().getData(handle)) {
            if (!chatformat.getName().equalsIgnoreCase(handleName))
                continue;

            chatFormatPlayerData = (ChatFormatPlayerData) chatformat;
        }

        if (chatFormatPlayerData == null)
            ExceptionUtil.throwException(new PlayerDataException("Can't find PlayerData '" + handle + "-" + handleName + "', please check is registered this PlayerData."));

        assert chatFormatPlayerData != null;

        float time = ChatFormatConstant.CHAT_DEFAULTTIME;
        if (args.length == 2) {
            if (!Character.isDigit(args[1].charAt(0))) {
                sender.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_MUTEWRONG)
                        .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                        .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString());
                return false;
            }

            time = Float.parseFloat(args[1]);
            chatFormatPlayerData.setPlayerData(player, new Object[]{DateUtil.getDate(ChatFormatConstant.SET_VARIABLE_DATEFORMAT), time});
        } else
            chatFormatPlayerData.setPlayerData(player, new Object[]{DateUtil.getDate(ChatFormatConstant.SET_VARIABLE_DATEFORMAT), 10});

        sender.sendMessage(new VariableFactory(ChatFormatConstant.MUTE_SUCCESS)
                        .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                        .player(player)
                        .time(String.valueOf(time))
                        .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                        .placeholder(player)
                        .color()
                        .getString());

        for (String s : ChatFormatConstant.CHAT_MUTE_MESSAGE) {
            player.sendMessage(new VariableFactory(s)
                            .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                            .player(player)
                            .time(String.valueOf(time))
                            .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                            .placeholder(player)
                            .color()
                            .getString());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (args.length > 2) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
            list.add(onlinePlayer.getName());

        List<String> conditionList = new ArrayList<>();
        for (String s1 : list) {
            if (!s1.startsWith(args[0]))
                continue;
            conditionList.add(s1);
        }

        if (conditionList.size() == 0)
            return list;
        return new ArrayList<>();
    }
}