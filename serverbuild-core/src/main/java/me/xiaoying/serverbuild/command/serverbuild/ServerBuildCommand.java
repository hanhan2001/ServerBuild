package me.xiaoying.serverbuild.command.serverbuild;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBModuleCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBReloadCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBScriptCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBStatusCommand;
import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.factory.VariableFactory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * ServerBuild Command
 */
@Command(values = {"sb", "serverbuild"}, length = -1)
public class ServerBuildCommand extends SCommand {
    public ServerBuildCommand() {
        this.registerCommand(new SBReloadCommand());
        this.registerCommand(new SBScriptCommand());
        this.registerCommand(new SBStatusCommand());
        this.registerCommand(new SBModuleCommand());
    }

    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(ConfigCommon.OVERALL_SITUATION_MESSAGE_HELP)
                .prefix(ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX)
                .date(ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        this.parentCommand(sender, strings);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = super.onTabComplete(sender, command, head, strings);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), "minecraft:block.bamboo.fall", 1F, 0F);
        }
        return list;
    }
}