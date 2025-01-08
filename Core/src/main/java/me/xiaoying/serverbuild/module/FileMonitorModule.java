package me.xiaoying.serverbuild.module;

import com.sun.nio.file.SensitivityWatchEventModifier;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileFileMonitor;
import me.xiaoying.serverbuild.inf.FileWatcherInterface;
import me.xiaoying.serverbuild.listener.FileMonitorListener;
import me.xiaoying.serverbuild.utils.ServerUtil;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Module FileMonitor
 */
public class FileMonitorModule extends Module {

    private final List<FileWatcherInterface> listeners = new ArrayList<>();
    private WatchService watchService;

    private ScheduledFuture<?> scheduledFuture = null;

    @Override
    public String getName() {
        return "文件监控";
    }

    @Override
    public String getAliasName() {
        return "FileMonitor";
    }

    @Override
    public boolean ready() {
        return FileFileMonitor.ENABLE;
    }

    @Override
    public void init() {
        this.registerFile(new FileFileMonitor());
    }

    @Override
    public void onEnable() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get(ServerUtil.getDataFolder().getAbsolutePath());
            path.register(this.watchService, new WatchEvent.Kind[]{StandardWatchEventKinds.OVERFLOW, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY}, SensitivityWatchEventModifier.HIGH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.listeners.add(new FileMonitorListener());

       this.scheduledFuture = SBPlugin.getExecutorService().scheduleAtFixedRate(this::watch, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        if (this.scheduledFuture != null)
            this.scheduledFuture.cancel(true);
    }

    private void watch() {
        WatchKey watchKey;
        try {
            watchKey = this.watchService.take();
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (WatchEvent<?> pollEvent : watchKey.pollEvents()) {
            WatchEvent<Path> curEvent = (WatchEvent<Path>) pollEvent;

            switch (pollEvent.kind().name()) {
                case "ENTRY_CREATE":
                    this.listeners.forEach(listener -> listener.onCreate(curEvent.context().toFile()));
                    break;
                case "ENTRY_DELETE":
                    this.listeners.forEach(listener -> listener.onDelete(curEvent.context().toFile()));
                    break;
                case "ENTRY_MODIFY":
                    this.listeners.forEach(listener -> listener.onChange(curEvent.context().toFile()));
                    break;
                case "OVERFLOW":
                    this.listeners.forEach(listener -> listener.onOverflow(curEvent.context().toFile()));
                    break;
            }
        }

        watchKey.reset();
    }
}