package me.xiaoying.serverbuild.inf;

import java.io.File;

public interface FileWatcherInterface {
    void onCreate(File file);

    void onDelete(File file);

    void onChange(File file);

    void onOverflow(File file);
}