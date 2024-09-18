package me.xiaoying.serverbuild.command.serverbuild.commands.modulecommand;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileConfig;
import me.xiaoying.serverbuild.module.Module;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Command(values = "on", length = 1)
public class SBModuleOnCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_HELP)
                .prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX)
                .date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION).prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX).date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT).placeholder(sender).color().toString());
            return;
        }

        Module module;
        if ((module = SBPlugin.getModuleManager().getModule(args[0])) == null) {
            sender.sendMessage(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_NOT_FOUND).prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX).date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT).placeholder(sender).color().toString());
            return;
        }

        if (module.isEnabled()) {
            sender.sendMessage(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_OPENED).prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX).date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT).placeholder(sender).color().toString());
            return;
        }

        module.enable();
        sender.sendMessage(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_MODULE_OPEN).prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX).date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT).placeholder(sender).color().toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>();
        SBPlugin.getModuleManager().getModules().forEach(module -> list.add(module.getAliasName()));

        List<String> conditionList = new ArrayList<>();
        for (String s : list) {
            if (!s.toUpperCase(Locale.ENGLISH).startsWith(strings[strings.length - 1].toUpperCase(Locale.ENGLISH)))
                continue;

            conditionList.add(s);
        }
        return conditionList;
    }
}
