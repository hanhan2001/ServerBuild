package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.utils.YamlUtil;
import me.xiaoying.serverbuild.utils.ZipUtil;

import java.io.File;
import java.util.*;

public class SimpleModuleManager implements ModuleManager {
    private final List<Module> knownModules = new ArrayList<>();

    @Override
    public void registerModule(Module module) {
        if (this.knownModules.contains(module))
            return;

        this.knownModules.add(module);
    }

    @Override
    public void unregisterModule(Module module) {
        if (!this.knownModules.contains(module))
            return;
        module.onDisable();
        module.disable();
        this.knownModules.remove(module);
    }

    @Override
    public void unregisterModules() {
        this.disableModules();
        this.knownModules.clear();
    }

    @Override
    public void loadModule(File file) {
        String plugin = ZipUtil.getFile(file.getAbsolutePath(), "plugin.yml");
        if (plugin == null || plugin.isEmpty())
            return;

        if (!YamlUtil.getNodes(plugin).contains("module"))
            return;
        if (!YamlUtil.getNodes(plugin, "module").contains("main"))
            return;

        try {
            Class<?> superClazz = this.getClass().getClassLoader().loadClass(YamlUtil.getValueByKey(plugin, "module.main").toString()).getSuperclass();
            while (superClazz != Object.class) {
                if (superClazz == Module.class)
                    break;

                superClazz = superClazz.getSuperclass();
            }
            if (superClazz != Module.class)
                return;

            this.registerModule((Module) superClazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadModules(File folder) {
        if (!folder.isDirectory())
            return;

        for (File file : Objects.requireNonNull(folder.listFiles()))
            this.loadModule(file);
    }

    @Override
    public Module getModule(String name) {
        for (Module knownModule : this.knownModules) {
            if (!knownModule.getAliasName().equalsIgnoreCase(name))
                continue;

            return knownModule;
        }
        return null;
    }

    @Override
    public List<Module> getModules() {
        return this.knownModules;
    }

    @Override
    public void enableModules() {
        this.knownModules.forEach(Module::enable);
    }

    @Override
    public void disableModules() {
        this.knownModules.forEach(module -> {
            module.onDisable();
            module.disable();
        });
    }
}