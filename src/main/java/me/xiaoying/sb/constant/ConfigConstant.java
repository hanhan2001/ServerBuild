package me.xiaoying.sb.constant;

import java.util.List;

public class ConfigConstant {
    /**
     * SET_BSTATS 是否上传使用插件数据
     * OVERALL_ENABLE 是否开启全局配置
     * OVERALL_ENABLE_VARIABLE 是否开启全局变量
     * OVERALL_ENABLE_MESSAGE 是否开启全局词条
     */
    public static Boolean SET_BSTATS,
            OVERALL_ENABLE,
            OVERALL_ENABLE_VARIABLE,
            OVERALL_ENABLE_MESSAGE;

    /**
     * OVERALL_VARIABLE_DATAFORMAT 全局变量 日期
     * OVERALL_MESSAGE_PREFIX 全局词条 前缀
     * OVERALL_MESSAGE_NOPERMISSION 全局词条 无权限
     */
    public static String OVERALL_VARIABLE_DATAFORMAT,
            OVERALL_MESSAGE_PREFIX,
            OVERALL_MESSAGE_RELOAD,
            OVERALL_MESSAGE_NOPERMISSION;

    public static List<String> MESSAGE_HELP;
}