package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.core.SBPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.security.InvalidParameterException;

public abstract class File {
    private final String path;
    private final java.io.File file;
    private YamlConfiguration configuration;

    public File(String file) {
        this.file = new java.io.File(SBPlugin.getInstance().getDataFolder(), file);
        this.path = file;
    }

    public File(String path, String name) {
        this.file = new java.io.File(path, name);
        this.path = name;
    }

    public java.io.File getParent() {
        return this.file.getParentFile();
    }

    public String getName() {
        return this.file.getName();
    }

    public java.io.File getFile() {
        return this.file;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public void load() {
        if (!this.file.exists()) {
            if (this.path.contains("/"))
                this.saveResource(this.path, false);
            else
                this.saveResource(this.file.getName(), false);
        }
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
        if (resourcePath == null || resourcePath.equals(""))
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null)
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");

        java.io.File outFile = new java.io.File(resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        java.io.File outDir = new java.io.File(resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists())
            outDir.mkdirs();

        try {
            if (outFile.exists() && !replace)
                SBPlugin.getInstance().getLogger().warning("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");

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