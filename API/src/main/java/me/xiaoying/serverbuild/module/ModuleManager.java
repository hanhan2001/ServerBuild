package me.xiaoying.serverbuild.module;

import java.io.File;
import java.util.List;

public interface ModuleManager {
    /**
     * Register module
     *
     * @param module Module
     */
    void registerModule(Module module);

    /**
     * Unregister module
     *
     * @param module Module
     */
    void unregisterModule(Module module);

    /**
     * Unregister all module
     */
    void unregisterModules();

    /**
     * Load module from file
     *
     * @param file file
     */
    void loadModule(File file);

    /**
     * Load modules from folder
     *
     * @param folder Folder file
     */
    void loadModules(File folder);

    /**
     * Get module by module's alias name
     *
     * @param name Module's alias name
     * @return Module
     */
    Module getModule(String name);

    /**
     * Get all registered module
     *
     * @return Module list
     */
    List<Module> getModules();

    /**
     * Enable all module
     */
    void enableModules();

    /**
     * Disable all module
     */
    void disableModules();
}