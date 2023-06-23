package me.xiaoying.sb.command;

/**
 * 指令 已注册指令
 */
public class RegisteredCommand {
    int length;
    SubCommand subCommand;

    public RegisteredCommand(int length, SubCommand subCommand) {
        this.length = length;
        this.subCommand = subCommand;
    }

    public int getLength() {
        return this.length;
    }

    public SubCommand getSubCommand() {
        return this.subCommand;
    }
}