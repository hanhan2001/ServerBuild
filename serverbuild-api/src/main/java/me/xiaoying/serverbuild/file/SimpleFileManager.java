package me.xiaoying.serverbuild.file;

import java.util.ArrayList;
import java.util.List;

public class SimpleFileManager implements FileManager {
    private final List<SFile> knownFiles = new ArrayList<>();

    @Override
    public SFile getFile(String file) {
        for (SFile knownSFile : this.knownFiles) {
            if (!knownSFile.getName().equalsIgnoreCase(file))
                continue;

            return knownSFile;
        }
        return null;
    }

    @Override
    public void register(SFile SFile) {
        if (this.knownFiles.contains(SFile))
            return;

        this.knownFiles.add(SFile);
    }

    @Override
    public void unregister(SFile SFile) {
         if (!this.knownFiles.contains(SFile))
             return;

         SFile.onDisable();
         this.knownFiles.remove(SFile);
    }

    @Override
    public void unregisterAll() {
        this.knownFiles.forEach(SFile::onDisable);
        this.knownFiles.clear();
    }

    @Override
    public void loads() {
        this.knownFiles.forEach(SFile::load);
    }

    @Override
    public void disables() {
        this.knownFiles.forEach(SFile::onDisable);
    }

    @Override
    public void deletes() {
        this.knownFiles.forEach(SFile::delete);
    }
}