package me.xiaoying.serverbuild.command.serverbuild;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.RegisteredCommand;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBModuleCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBReloadCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBScriptCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.SBStatusCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ServerBuild Command
 */
@Command(values = {"sb", "serverbuild"}, length = -1, description = "ServerBuild 主命令")
public class ServerBuildCommand extends SCommand {
    public ServerBuildCommand() {
        this.registerCommand(new SBReloadCommand());
        this.registerCommand(new SBScriptCommand());
        this.registerCommand(new SBStatusCommand());
        this.registerCommand(new SBModuleCommand());
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        // 判断命令长度
        if (strings.length == 0) {
            this.getHelpMessage().forEach(sender::sendMessage);
            return;
        }

        // 判断是否存在相应命令
        String head = strings[0];
        if (!this.getRegisteredCommands().containsKey(head)) {
            this.getHelpMessage().forEach(sender::sendMessage);
            return;
        }

        // 移除 head
        List<String> list = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length));
        strings = list.toArray(new String[0]);

        boolean isDo = false;
        for (RegisteredCommand registeredCommand : this.getRegisteredCommands().get(head)) {
            if (registeredCommand.getLength() != strings.length && registeredCommand.getLength() != -1)
                continue;

            registeredCommand.getSubCommand().performCommand(sender, strings);
            isDo = true;
        }

        if (isDo)
            return;

        // 未执行则发出帮助信息
        this.getRegisteredCommands().get(head).get(0).getSubCommand().getHelpMessage().forEach(sender::sendMessage);
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