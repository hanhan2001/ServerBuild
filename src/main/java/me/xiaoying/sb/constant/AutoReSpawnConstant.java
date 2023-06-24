package me.xiaoying.sb.constant;

import java.util.List;

public class AutoReSpawnConstant {
    /**
     * ENABLE_AUTORESPAWN 自动重生
     * ENABLE_AUTORESPAWN_CHAT 自动重生聊天框提示
     * ENABLE_AUTORESPAWN_TITLE 标题提示
     * ENABLE_AUTORESPAWN_ACTIONBAR ActionBar提示
     */
    public static boolean SET_ENABLE,
            ENABLE_CHAT,
            ENABLE_TITLE,
            ENABLE_ACTIONBAR;

    /**
     * AUTORESPAWN_CHECK_TIME 检测时间
     * AUTORESPAWN_TITLE_FADE_IN 标题 淡入
     * AUTORESPAWN_TITLE_STAY 标题 停留
     * AUTORESPAWN_TITLE_FADEOUT 标题 淡出,
     * AUTORESPAWN_ACTIONBAR_TIME Actionbar 持续时间
     */
    public static int CHECK_TIME,
            TITLE_FADE_IN,
            TITLE_STAY,
            TITLE_FADEOUT,
            ACTIONBAR_TIME;

    /**
     * AUTORESPAWN_DATEFORMAT 日期格式
     * AUTORESPAWN_TITLE_TITLE 标题 标题
     * AUTORESPAWN_TITLE_SUBTITLE 标题 副标题
     * AUTORESPAWN_ACTIONBAR_MESSAGE ActionBar 消息
     * AUTORESPAWN_MESSAGE_PREFIX 前缀
     * AUTORESPAWN_MESSAGE_RELOAD 重载
     * AUTORESPAWN_MESSAGE_NOPERMISSION 无权限
     */
    public static String SET_VARIABLE_DATEFORMAT,
            TITLE_TITLE,
            TITLE_SUBTITLE,
            ACTIONBAR_MESSAGE,
            MESSAGE_PREFIX,
            MESSAGE_RELOAD,
            MESSAGE_NOPERMISSION;

    public static List<String> CHAT_FORMAT, MESSAGE_HELP;
}