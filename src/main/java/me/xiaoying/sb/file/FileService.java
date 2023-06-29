package me.xiaoying.sb.file;

import java.util.HashMap;
import java.util.Map;

public class FileService {
    private final Map<String, SubFile> files = new HashMap<>();

    public void fileAll() {
        for (SubFile file : this.files.values())
            file.newFile();
    }

    public void file(String name) {
        this.files.get(name.toUpperCase()).newFile();
    }

    public void delAll() {
        for (SubFile file : this.files.values())
            file.delFile();
    }

    public void del(String name) {
        this.files.get(name.toUpperCase()).delFile();
    }

    public void init(String name) {
        this.files.get(name.toUpperCase()).initFile();
    }

    public void initAll() {
        for (SubFile file : this.files.values())
            file.initFile();
    }

    public void register(String name, SubFile task) {
        this.files.put(name.toUpperCase(), task);
    }

    public void unregister(String name) {
        this.files.remove(name);
    }

    public void unregisters() {
        this.files.clear();
    }
}