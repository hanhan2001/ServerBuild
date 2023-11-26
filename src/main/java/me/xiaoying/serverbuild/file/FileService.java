package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.utils.ServerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * FileService
 */
public class FileService {
    Map<String, SubFile> files = new HashMap<>();

    /**
     * 注册 File
     *
     * @param name File name
     * @param file File
     */
    public void register(String name, SubFile file) {
        if (this.files.containsKey(name)) {
            ServerUtil.sendMessage("&e已注册过File " + file.getName() + " 请检查代码是否有重复操作", true);
            return;
        }
        this.files.put(name, file);
    }

    /**
     * 取消注册
     *
     * @param name File name
     */
    public void unregister(String name) {
        this.files.remove(name);
    }

    /**
     * 取消注册所有 File
     */
    public void unregisters() {
        this.files.clear();
    }

    /**
     * 获取 File
     *
     * @param name File name
     * @return File
     */
    public SubFile getFile(String name) {
        return this.files.get(name);
    }

    /**
     * 获取所有 File
     *
     * @return Map
     */
    public Map<String, SubFile> getFiles() {
        return files;
    }

    /**
     * 生成 File
     *
     * @param name File name
     */
    public void file(String name) {
        if (!this.files.containsKey(name))
            return;

        this.files.get(name).delete();
    }

    /**
     * 生成所有 File
     */
    public void fileAll() {
        for (SubFile value : this.files.values())
            value.file();
    }

    /**
     * 初始化 File
     *
     * @param name File name
     */
    public void initialize(String name) {
        if (!this.files.containsKey(name))
            return;

        this.files.get(name).initialize();
    }

    /**
     * 初始化所有 File
     */
    public void initializeAll() {
        for (SubFile value : this.files.values())
            value.initialize();
    }

    /**
     * 删除 File
     *
     * @param name File name
     */
    public void delete(String name) {
        if (!this.files.containsKey(name))
            return;

        this.files.get(name).delete();
    }

    /**
     * 删除所有 File
     */
    public void deleteAll() {
        for (SubFile value : this.files.values())
            value.delete();
    }
}