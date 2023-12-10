package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.script.Script;
import me.xiaoying.serverbuild.utils.ServerUtil;

import java.util.List;

/**
 * Script Log
 */
public class LogScript implements Script {
    @Override
    public String command() {
        return "log";
    }

    @Override
    public List<String> alias() {
        return null;
    }

    @Override
    public void performCommand(String[] args) {
        // 拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }

        ServerUtil.sendMessage(stringBuilder.toString(), true);
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}