package me.xiaoying.serverbuild.command.resolvelag;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.resolvelag.commands.RLEntityCommand;
import me.xiaoying.serverbuild.command.resolvelag.commands.RLGcCommand;
import me.xiaoying.serverbuild.command.resolvelag.commands.RLReloadCommand;
import me.xiaoying.serverbuild.command.resolvelag.commands.RLStateCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Command(values = {"rl", "resolvelag"}, length = -1)
public class ResolveLagCommand extends SCommand {
    public ResolveLagCommand() {
        this.registerCommand(new RLStateCommand());
        this.registerCommand(new RLEntityCommand());
        this.registerCommand(new RLReloadCommand());
        this.registerCommand(new RLGcCommand());
    }

    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileResolveLag.MESSAGE_HELP)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
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