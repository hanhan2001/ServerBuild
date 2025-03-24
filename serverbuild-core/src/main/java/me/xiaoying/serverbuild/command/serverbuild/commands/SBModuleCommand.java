package me.xiaoying.serverbuild.command.serverbuild.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.RegisteredCommand;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.modulecommand.SBModuleInfoCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.modulecommand.SBModuleOffCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.modulecommand.SBModuleOpenCommand;
import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileConfig;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Command(values = "module", length = 2, parameters = {"info/off/open", "Module"})
public class SBModuleCommand extends SCommand {
    public SBModuleCommand() {
        this.registerCommand(new SBModuleOpenCommand());
        this.registerCommand(new SBModuleOffCommand());
        this.registerCommand(new SBModuleInfoCommand());
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
        this.getHelpMessage().forEach(sender::sendMessage);
    }
}