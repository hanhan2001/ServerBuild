package me.xiaoying.sb.service.scriptcommand;

import me.xiaoying.sb.service.scriptcommand.command.ConsoleCommand;
import me.xiaoying.sb.service.scriptcommand.command.DelayCommand;
import me.xiaoying.sb.service.scriptcommand.command.ForCommand;
import me.xiaoying.sb.service.scriptcommand.command.SendCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 命令 脚本命令处理
 */
public class ScriptCommandService {
    private final List<ScriptCommand> mSubcommandList = new ArrayList<>();


    /**
     * 命令 匹配命令
     *
     * @param command 命令
     *
     * @param player 玩家
     */
    public void onCommand(String command, Player player) {
        if (command == null || command.isEmpty())
            return;

        ScriptCommand subCommand = null;
        for (ScriptCommand mSubcommand : mSubcommandList) {
            if (!command.split(" ")[0].equalsIgnoreCase(mSubcommand.command()))
                continue;

            subCommand = mSubcommand;
            break;
        }

        String[] commandList = command.split(" ");

        List<String> list = new ArrayList<>(Arrays.asList(commandList).subList(1, commandList.length));
        commandList = list.toArray(new String[0]);

        assert subCommand != null;
        subCommand.performCommand(player, commandList);
    }

    /**
     * 命令 注册所有命令(内置写死不可更改)
     */
    public void registerCommands() {
        this.mSubcommandList.add(new ForCommand());
        this.mSubcommandList.add(new SendCommand());
        this.mSubcommandList.add(new DelayCommand());
        this.mSubcommandList.add(new ConsoleCommand());
    }

    /**
     * 命令注册接口
     *
     * @param mSubCommand 类
     */
    public void registerCommand(ScriptCommand mSubCommand) {
        this.mSubcommandList.add(mSubCommand);
    }
}