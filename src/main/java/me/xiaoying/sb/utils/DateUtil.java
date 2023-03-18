package me.xiaoying.sb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类 日期
 */
public class DateUtil {
    /**
     * 获取格式日期
     *
     * @param format 格式
     * @return String*Date
     */
    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 日期相减
     *
     * @param date 日期 1
     * @param date1 日期 2
     * @return 减后时间
     */
    public static long getDateReduce(Date date, Date date1) {
        return date.getTime() - date1.getTime();
    }
}