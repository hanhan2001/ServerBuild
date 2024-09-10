package me.xiaoying.serverbuild.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 工具类 ZIP压缩包
 */
public class ZipUtil {
    /**
     * 获取ZIP中的某个文件
     *
     * @param file 压缩文件路径
     * @param fileName 文件名称
     */
    public static String getFile(String file, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            //获取文件输入流
            FileInputStream input = new FileInputStream(file);
            //获取ZIP输入流(一定要指定字符集Charset.forName("GBK")否则会报java.lang.IllegalArgumentException: MALFORMED)
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(input), StandardCharsets.UTF_8);
            //定义ZipEntry置为null,避免由于重复调用zipInputStream.getNextEntry造成的不必要的问题
            ZipEntry ze = null;

            //循环遍历
            while ((ze = zipInputStream.getNextEntry()) != null) {
                //读取
                BufferedReader br = new BufferedReader(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8));
                String line;
                if (ze.getName().equals(fileName)) {
                    //内容不为空，输出
                    while ((line = br.readLine()) != null)
                        stringBuilder.append(line).append("\n");
                    break;
                }
            }

            //一定记得关闭流
            zipInputStream.closeEntry();
            input.close();
        } catch (IOException e) {}
        return String.valueOf(stringBuilder);
    }
}