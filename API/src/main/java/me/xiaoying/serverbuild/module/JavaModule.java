package me.xiaoying.serverbuild.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class JavaModule extends Module {
    private JavaModuleLoader loader;
    private ModuleDescription description;
    private ClassLoader classLoader;
    private java.io.File file;
    private boolean isEnabled;

    public JavaModule() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof ModuleClassLoader))
            throw new IllegalStateException("JavaModule requires " + ModuleClassLoader.class.getName());
        else
            ((ModuleClassLoader) classLoader).initialize(this);
    }

    public JavaModule(JavaModuleLoader loader, ModuleDescription description, java.io.File file, ModuleClassLoader classLoader) {
        this.loader = loader;
        this.description = description;
        this.classLoader = classLoader;
        this.file = file;
    }

    public void initialize(JavaModuleLoader loader, ModuleDescription description, java.io.File file, ModuleClassLoader classLoader) {
        this.loader = loader;
        this.description = description;
        this.classLoader = classLoader;
        this.file = file;
    }

    @Override
    public String getName() {
        return this.description.getName();
    }

    @Override
    public String getAliasName() {
        return this.description.getAlias();
    }

    /**
     * Get module description
     *
     * @return ModuleDescription
     */
    public ModuleDescription getDescription() {
        return this.description;
    }

    public ModuleLoader getModuleLoader() {
        return this.loader;
    }

    /**
     * Get file of module
     *
     * @return File
     */
    public File getFile() {
        return this.file;
    }

    protected final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    public void init() {
        this.onLoad();
    }

    protected final void setEnabled(boolean enabled) {
        if (this.isEnabled == enabled)
            return;

        this.isEnabled = enabled;
        if (this.isEnabled)
            this.onEnable();
        else
            this.onDisable();
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public abstract void onLoad();

    /**
     * Get file in jar package
     *
     * @param filename file's name
     * @return InputStream
     */
    public InputStream getResource(String filename) {
        if (filename == null)
            throw new IllegalArgumentException("Filename cannot be null");

        try {
            URL url = this.getClassLoader().getResource(filename);

            if (url == null)
                return null;

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException iOException) {
            return null;
        }
    }
}