package me.xiaoying.sb.command.chatformatcommand.subcommand;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.metadata.MuteMetaData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Command(values = "mute", length = {1, 2})
public class CFMuteCommand extends SubCommand {
    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        Player player = Bukkit.getServer().getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_NOTFOUNPAYER)
                            .color()
                            .player(args[0])
                            .prefix(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                            .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                            .getString());
            return false;
        }

        if (player.getMetadata("mute").size() != 0)
            player.removeMetadata("mute", ServerBuild.getInstance());

        float time = ChatFormatConstant.CHAT_DEFAULTTIME;
        if (args.length == 2) {
            if (!Character.isDigit(args[1].charAt(0))) {
                sender.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_MUTEWRONG)
                        .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                        .color()
                        .getString());
                return false;
            }

            time = Float.parseFloat(args[1]);
            player.setMetadata("mute", new MuteMetaData(time, TimeUnit.SECONDS));
        } else
            player.setMetadata("mute", new MuteMetaData(time, TimeUnit.SECONDS));

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
                    .time(player.getMetadata("mute").get(0).asString())
                    .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                    .placeholder(player)
                    .color()
                    .getString());
        }
        return false;
    }
}