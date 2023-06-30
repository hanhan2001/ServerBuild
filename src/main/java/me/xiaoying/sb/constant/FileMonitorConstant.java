package me.xiaoying.sb.constant;

import me.xiaoying.sb.utils.ServerUtil;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.util.List;

public class FileMonitorConstant {

    public static FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(ServerUtil.getDataFolder());
    public static FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500, fileAlterationObserver);
    public static boolean MONITOR_ENABLE = false;

    public static boolean SET_ENABLE,
            SET_MESSAGE_CONSOLE,
            SET_MESSAGE_OPERATOR,
            SET_MESSAGE_OPERATOR_CHAT,
            SET_MESSAGE_OPERATOR_TITLE,
            SET_MESSAGE_OPERATOR_ACTIONBAR;

    public static String SET_VARIABLE_DATEFORMAT,
            MESSAGE_PREFIX,
            MESSAGE_RELOAD,
            MESSAGE_UPDATE,
            MESSAGE_OPERATOR_TITLE_TITLE,
            MESSAGE_OPERATOR_TITLE_SUBTITLE,
            MESSAGE_OPERATOR_ACTIONBAR,
            MESSAGE_NOPERMISSION;
    public static List<String> MESSAGE_OPERATOR_CHAT,
            MESSAGE_HELP;
}