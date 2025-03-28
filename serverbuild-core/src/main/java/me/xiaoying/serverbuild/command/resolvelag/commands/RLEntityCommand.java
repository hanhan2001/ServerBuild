package me.xiaoying.serverbuild.command.resolvelag.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.RegisteredCommand;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.resolvelag.commands.entityclear.RLEntityClearCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Command(values = "entityclear", length = -1)
public class RLEntityCommand extends SCommand {
    public RLEntityCommand() {
        this.registerCommand(new RLEntityClearCommand());
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