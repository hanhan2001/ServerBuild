package me.xiaoying.sb.service.scriptcommand.command;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.scriptcommand.ScriptCommand;
import me.xiaoying.sb.utils.ServerBuildUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * 命令 for
 */
public class ForCommand extends ScriptCommand {
    @Override
    public String command() {
        return "for";
    }

    @Override
    public boolean performCommand(Player player, String[] args) {
        // 判断语句格式是否完整
        if (args.length < 3) {
            ServerUtil.sendMessage(new VariableFactory("%preifx%&cfor参数不完整！")
                    .prefix(ConfigConstant.OVERALL_MESSAGE_PREFIX)
                    .getString(), true);
            return false;
        }

        if (!Character.isDigit(args[0].charAt(0))) {
            ServerBuildUtil.errorInfo("&cfor循环次数填写错误！", "循环命令", Arrays.toString(args));
            return false;
        }

        if (!args[1].equalsIgnoreCase("->")) {
            ServerBuildUtil.errorInfo("&c缺少关键字符: ->！", "循环命令", Arrays.toString(args));

            ServerUtil.sendMessage(new VariableFactory("%preifx%&c缺少关键字符: ->")
                    .prefix(ConfigConstant.OVERALL_MESSAGE_PREFIX)
                    .getString(), true);
            return false;
        }

        // 编辑待循环命令
        int time = Integer.parseInt(args[0]);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i == 0 || i == 1) continue;
            if (i == args.length - 1) {
                stringBuilder.append(args[i]);
                break;
            }
            stringBuilder.append(args[i]).append(" ");
        }
        // 循环命令
        for (int i = 0; i < time; i++)
            ServerBuild.getScriptCommandService().onCommand(stringBuilder.toString(), player);
        return false;
    }
}