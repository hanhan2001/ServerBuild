package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.configuration.ConfigurationSerializeble;
import me.xiaoying.serverbuild.configuration.YamlConfiguration;
import me.xiaoying.serverbuild.configuration.serialization.ConfigurationSerializable;
import me.xiaoying.serverbuild.configuration.serialization.ConfigurationSerialization;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.utils.Preconditions;
import me.xiaoying.serverbuild.utils.ZipUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class JavaModuleLoader implements ModuleLoader {
    private final Map<String, Class<?>> classes = new ConcurrentHashMap<>();
    private final Map<String, ModuleClassLoader> loaders = new LinkedHashMap<>();
    private final Pattern[] fileFilters = new Pattern[] {Pattern.compile("\\.jar$")};

    public JavaModuleLoader() {}

    @Override
    public JavaModule loadModule(File file) throws InvalidModuleException {
        Preconditions.checkArgument(file != null, "File cannot be null");

        ModuleDescription description;
        ModuleClassLoader loader;


        if (!file.exists())
            throw new InvalidModuleException(new FileNotFoundException(file.getPath() + " does not exists"));

        try {
            description = this.getModuleDescription(file);
        } catch (InvalidDescriptionException e) {
            throw new RuntimeException(e);
        }

        if (description == null)
            return null;

        try {
            loader = new ModuleClassLoader(this, description, this.getClass().getClassLoader(), file, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.module;
    }

    @Override
    public ModuleDescription getModuleDescription(File file) throws InvalidDescriptionException {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(ZipUtil.getFile(file.getAbsolutePath(), "plugin.yml"));
        String module = yamlConfiguration.getString("module");
        if (module == null || module.isEmpty())
            return null;
        String name = yamlConfiguration.getString("module.name");
        Preconditions.checkNotNull(name, "Module name cannot be null from " + file.getName());
        String alias = yamlConfiguration.getString("module.alias");
        Preconditions.checkNotNull(alias, "Module alias cannot be null from " + file.getName());
        String main = yamlConfiguration.getString("module.main");
        Preconditions.checkNotNull(main, "Main class cannot be null from " + file.getName());
        String version = yamlConfiguration.getString("module.version");
        Preconditions.checkNotNull(version, "Module version cannot be null from " + file.getName());
        List<String> authors = new ArrayList<>();
        if (yamlConfiguration.getString("module.author") != null && !yamlConfiguration.getString("module.author").isEmpty())
            authors.add(yamlConfiguration.getString("module.author"));

        if (yamlConfiguration.getStringList("module.authors") != null && !yamlConfiguration.getStringList("module.authors").isEmpty())
            authors.addAll(yamlConfiguration.getStringList("module.authors"));
        String description = yamlConfiguration.getString("module.description");
        return new ModuleDescription(name, alias, main, version, authors.toArray(new String[0]), description);
    }

    @Override
    public Pattern[] getPluginFileFilters() {
        return this.fileFilters;
    }

    @Override
    public void enableModule(JavaModule module) {
        Preconditions.isTrue(module instanceof JavaModule, "Module is not associated with this PluginLoader");

        if (module.isEnabled())
            return;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < module.getDescription().getAuthors().length; i++) {
            stringBuilder.append(module.getDescription().getAuthors()[i]);

            if (i == module.getDescription().getAuthors().length - 1)
                break;

            stringBuilder.append(", ");
        }

        String message = String.format("Enabling %s %s by %s", module.getDescription().getName(), module.getDescription().getVersion(), stringBuilder);
        SBPlugin.getInstance().getLogger().info(message);

        String pluginName = module.getDescription().getName();

        if (!this.loaders.containsKey(pluginName))
            this.loaders.put(pluginName, (ModuleClassLoader) module.getClassLoader());

        try {
            module.setEnabled(true);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void disableModule(JavaModule module) {
        Preconditions.isTrue(module instanceof JavaModule, "Plugin is not associated with this PluginLoader");

        if (!module.isEnabled())
            return;

        String message = String.format("Disabling %s", module.getDescription().getName());
        SBPlugin.getInstance().getLogger().info(message);

//        this.server.getPluginManager().callEvent(new PluginDisableEvent(plugin));

        ClassLoader cloader = module.getClassLoader();
        try {
            module.setEnabled(false);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        this.loaders.remove(module.getDescription().getName());
        if (!(cloader instanceof ModuleClassLoader))
            return;

        ModuleClassLoader loader = (ModuleClassLoader) cloader;
        Set<String> names = loader.getClasses();

        for (String name : names)
            this.removeClass(name);
    }

    private void removeClass(String name) {
        Class<?> clazz = this.classes.remove(name);

        try {
            if (clazz != null && ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.unregisterClass(serializable);
            }
        } catch (NullPointerException nullPointerException) {}
    }

    Class<?> getClassByName(String name) {
        Class<?> cachedClass = this.classes.get(name);
        if (cachedClass != null)
            return cachedClass;

        for (String s : this.loaders.keySet()) {
            ModuleClassLoader loader = this.loaders.get(s);

            try { cachedClass = loader.findClass(name, false); } catch (ClassNotFoundException e) {}
            if (cachedClass != null)
                return cachedClass;
        }
        return null;
    }

    void setClass(String name, Class<?> clazz) {
        if (this.classes.containsKey(name))
            return;

        this.classes.put(name, clazz);
        if (ConfigurationSerializeble.class.isAssignableFrom(clazz)) {
            Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
            ConfigurationSerialization.registerClass(serializable);
        }
    }
}
