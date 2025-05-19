package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.core.SBPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public abstract class SFile {
    private final String outFolder;
    private final String resourcesFile;
    private final File file;
    private YamlConfiguration configuration;

    public SFile(String resourceFile) {
        this(resourceFile, SBPlugin.getInstance().getDataFolder().getAbsolutePath());
    }

    public SFile(String resourceFile, String outFolder) {
        this.resourcesFile = resourceFile;
        this.outFolder = outFolder;

        String fileName = resourceFile;
        if (resourceFile.contains("/") || resourceFile.contains("\\"))
            fileName = resourceFile.substring(resourceFile.lastIndexOf('/') + 1);

        if (outFolder != null && !outFolder.isEmpty())
            this.file = new File(this.outFolder, fileName);
        else
            this.file = new File(SBPlugin.getInstance().getDataFolder(), fileName);
    }

    public File getParent() {
        return this.file.getParentFile();
    }

    public String getName() {
        return this.file.getName();
    }

    public File getFile() {
        return this.file;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public void load() {
        if (!this.file.exists())
            this.saveResource(this.resourcesFile, false);

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
        this.onLoad();
    }

    public void delete() {
        this.file.delete();
        this.onDelete();
    }

    public void disable() {
        this.onDisable();
    }

    public abstract void onLoad();
    public abstract void onDelete();
    public abstract void onDisable();

    private InputStream getResource(String filename) {
        try {
            URL url = this.getClass().getClassLoader().getResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }

    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.isEmpty())
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = this.getResource(resourcePath);
        if (in == null)
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");

        File outFile = new File(this.outFolder, this.file.getName());
        if (!outFile.getParentFile().exists()) outFile.getParentFile().mkdirs();

        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists())
            outDir.mkdirs();

        try {
            if (outFile.exists() && !replace) {
                SBPlugin.getInstance().getLogger().warning("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                return;
            }

            OutputStream out = Files.newOutputStream(outFile.toPath());
            byte[] buf = new byte[in.available()];
            int len;
            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);

            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}