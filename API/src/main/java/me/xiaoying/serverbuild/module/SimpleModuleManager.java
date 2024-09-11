package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.utils.Preconditions;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleModuleManager implements ModuleManager {
    private final List<Module> knownModules = new ArrayList<>();
    private final Map<String, Module> lookupNames = new HashMap<>();
    private final Map<Pattern, ModuleLoader> fileAssociations = new HashMap<>();

    @Override
    public void registerInterface(Class<? extends ModuleLoader> loader) throws IllegalArgumentException {
        ModuleLoader moduleLoader;
        if (!ModuleLoader.class.isAssignableFrom(loader))
            throw new IllegalArgumentException(String.format("Class %s does not implement interface PluginLoader", loader.getName()));

        try {
            Constructor<? extends ModuleLoader> constructor = loader.getConstructor();
            moduleLoader = constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Pattern[] patterns = moduleLoader.getPluginFileFilters();

        synchronized (this) {
            for (Pattern pattern : patterns)
                this.fileAssociations.put(pattern, moduleLoader);
        }
    }

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
    public JavaModule loadModule(File file) {
        Preconditions.checkArgument(file != null, "File cannot be null");
        Set<Pattern> filters = this.fileAssociations.keySet();
        JavaModule module = null;

        for (Pattern filter : filters) {
            String name = file.getName();

            Matcher match = filter.matcher(name);
            if (match.find()) {
                ModuleLoader loader = this.fileAssociations.get(filter);
                try { module = loader.loadModule(file); } catch (InvalidModuleException e) { throw new RuntimeException(e); }
            }
        }

        if (module != null) {
            this.knownModules.add(module);
            this.lookupNames.put(module.getDescription().getName(), module);
        }

        return module;
    }

    @Override
    public Module[] loadModules(File folder) {
        Preconditions.checkArgument(folder != null, "Directory cannot be null");
        Preconditions.checkArgument(folder.isDirectory(), "Directory must be a directory");

        assert folder.listFiles() != null;

        if (folder.listFiles() == null || Objects.requireNonNull(folder.listFiles()).length == 0)
            return null;

        for (File file : Objects.requireNonNull(folder.listFiles()))
            this.loadModule(file);
        return new ArrayList<>(this.knownModules).toArray(new Module[0]);
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