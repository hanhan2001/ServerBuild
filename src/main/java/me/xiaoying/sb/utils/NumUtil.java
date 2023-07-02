package me.xiaoying.sb.utils;

/**
 * 工具类 数字
 */
public class NumUtil {
    /**
     * 是否为数字
     *
     * @param str 字符串
     * @return 逻辑值
     */
    public static boolean isNum(String str) {
        return Character.isDigit(str.charAt(0));
    }
}