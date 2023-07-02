package me.xiaoying.sb.service.scriptcommand.command;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.scriptcommand.ScriptCommand;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.entity.Player;

/**
 * 命令 send
 */
public class SendCommand extends ScriptCommand {
    @Override
    public String command() {
        return "send";
    }

    @Override
    public boolean performCommand(Player player, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i == args.length - 1) {
                stringBuilder.append(args[i]);
                break;
            }
            stringBuilder.append(args[i]).append(" ");
        }
        ServerUtil.onlinePlayersSendMessage(new VariableFactory(stringBuilder.toString())
                        .prefix(ConfigConstant.OVERALL_MESSAGE_PREFIX)
                        .date(ConfigConstant.OVERALL_VARIABLE_DATAFORMAT)
                        .placeholder(player)
                        .color()
                        .getString());
        return false;
    }
}