package me.xiaoying.serverbuild.command.welcomemessage;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.RegisteredCommand;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.welcomemessage.commands.WMReloadCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileWelcomeMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Command(values = {"wm", "welcomemessage"}, length = 1)
public class WelcomeMessageCommand extends SCommand {
    public WelcomeMessageCommand() {
        this.registerCommand(new WMReloadCommand());
    }

    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileWelcomeMessage.MESSAGE_HELP)
                .prefix(FileWelcomeMessage.SETTING_PREFIX)
                .date(FileWelcomeMessage.SETTING_DATEFORMAT)
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