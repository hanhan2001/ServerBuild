package me.xiaoying.sb.files;

import me.xiaoying.sb.files.config.FileConfig;
import me.xiaoying.sb.files.config.FileLoginTp;
import me.xiaoying.sb.files.config.FileNotBuild;
import me.xiaoying.sb.files.config.FileWelcomeMessage;

/**
 * 配置文件 管理中心
 */
public class FileManager {
    public static void fileManager() {
        FileConfig.fileConfig();
        FileLoginTp.fileConfig();
        FileNotBuild.fileNotBuild();
        FileWelcomeMessage.fileWelcomeMessage();
    }
}