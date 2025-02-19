package me.xiaoying.serverbuild.manager;

import com.sun.nio.file.SensitivityWatchEventModifier;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileFileMonitor;
import me.xiaoying.serverbuild.inf.FileWatcherInterface;
import me.xiaoying.serverbuild.listener.FileMonitorListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FileMonitorManager {
    private final List<FileWatcherInterface> listeners = new ArrayList<>();

    private final WatchService watchService;

    private final Map<WatchKey, Path> registeredPaths;

    private final ScheduledFuture<?> scheduledFuture;

    public FileMonitorManager() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.registeredPaths = new HashMap<>();

        this.listeners.add(new FileMonitorListener());

        this.scheduledFuture = SBPlugin.getExecutorService().scheduleAtFixedRate(this::watch, 0, 100, TimeUnit.MICROSECONDS);
    }

    /**
     * Register directory monitory
     *
     * @param directory Directory path
     */
    public void registerMonitorPath(String directory) {
        try {
            Path path = Paths.get(directory);
            WatchKey key = path.register(this.watchService, new WatchEvent.Kind[]{StandardWatchEventKinds.OVERFLOW, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY}, SensitivityWatchEventModifier.HIGH);
            this.registeredPaths.put(key, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void watch() {
        WatchKey watchKey;
        try {
            watchKey = this.watchService.take();
            Thread.sleep(FileFileMonitor.FILE_MONITOR_INTERVAL_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (WatchEvent<?> pollEvent : watchKey.pollEvents()) {
            WatchEvent<Path> curEvent = (WatchEvent<Path>) pollEvent;

            File file = new File(this.registeredPaths.get(watchKey).toFile(), curEvent.context().getFileName().toString());

            switch (pollEvent.kind().name()) {
                case "ENTRY_CREATE":
                    this.listeners.forEach(listener -> listener.onCreate(file));
                    break;
                case "ENTRY_DELETE":
                    this.listeners.forEach(listener -> listener.onDelete(file));
                    break;
                case "ENTRY_MODIFY":
                    this.listeners.forEach(listener -> listener.onChange(file));
                    break;
                case "OVERFLOW":
                    this.listeners.forEach(listener -> listener.onOverflow(file));
                    break;
            }
        }

        watchKey.reset();
    }

    public void close() {
        if (this.scheduledFuture != null)
            this.scheduledFuture.cancel(true);

        this.listeners.clear();
        this.registeredPaths.clear();

        try {
            this.watchService.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}