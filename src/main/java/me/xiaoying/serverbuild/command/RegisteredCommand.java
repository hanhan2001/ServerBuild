package me.xiaoying.serverbuild.command;

/**
 * Command RegisteredCommand
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