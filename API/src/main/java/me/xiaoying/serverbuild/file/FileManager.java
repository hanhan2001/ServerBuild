package me.xiaoying.serverbuild.file;

public interface FileManager {
    SFile getFile(String file);

    void register(SFile SFile);

    void unregister(SFile SFile);

    void unregisterAll();

    void loads();

    void disables();

    void deletes();
}