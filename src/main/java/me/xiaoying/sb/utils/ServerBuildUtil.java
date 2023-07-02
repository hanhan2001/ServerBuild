package me.xiaoying.sb.utils;

import me.xiaoying.sb.constant.ConfigConstant;

public class ServerBuildUtil {
    public static void errorInfo(String error, String from, String command) {
        String errorInfo = "%prefix%&c%error%" +
                "&c来源: &e%from%\n" +
                "&c命令: &e%command%\n";
        errorInfo = errorInfo.replace("%prefix%", ConfigConstant.OVERALL_MESSAGE_PREFIX);
        errorInfo = errorInfo.replace("%error%", error);
        errorInfo = errorInfo.replace("%from%", from);
        errorInfo = errorInfo.replace("%command%", command);
        ServerUtil.sendMessage(errorInfo, true);
    }
}