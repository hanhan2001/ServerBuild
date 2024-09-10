package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.utils.Preconditions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleClassLoader extends URLClassLoader {
    private final JavaModuleLoader loader;
    private final ModuleDescription description;
    private final File file;
    private final ClassLoader libraryLoader;
    JavaModule module;
    private JavaModule moduleInit;
    private IllegalStateException moduleState;
    private final Map<String, Class<?>> classes = new ConcurrentHashMap<>();

    public ModuleClassLoader(JavaModuleLoader loader, ModuleDescription description, ClassLoader classLoader, File file, ClassLoader libraryLoader) throws IOException, InvalidModuleException {
        super(new URL[]{ file.toURI().toURL() }, classLoader);
        this.loader = loader;
        this.file = file;
        this.libraryLoader = libraryLoader;
        this.description = description;

        try {
            Class<?> jarClass;
            try {
                jarClass = Class.forName(description.getMain(), true, this);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


            Class<?> moduleClass;
            try {
                moduleClass = jarClass.asSubclass(JavaModule.class);
            } catch (ClassCastException e) {
                throw new InvalidModuleException("main class `" + description.getMain() + "` dose not extends JavaModule", e);
            }

            this.module = (JavaModule) moduleClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("No public constructor", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Abnormal plugin type", e);
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return this.findClass(name, true);
    }

    Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("me.xiaoying.serverbuild."))
            throw new ClassNotFoundException(name);

        Class<?> result = this.classes.get(name);
        if (result != null)
            return result;
        if (checkGlobal)
            result = this.loader.getClassByName(name);
        if (result == null) {
            result = super.findClass(name);
            if (result != null) this.loader.setClass(name, result);
        }
        this.classes.put(name, result);
        return result;
    }

    Set<String> getClasses() {
        return this.classes.keySet();
    }

    synchronized void initialize(JavaModule javaModule) {
        Preconditions.checkArgument(javaModule != null, "Initializing module cannot be null");
        Preconditions.checkArgument(javaModule.getClass().getClassLoader() == this, "Cannot initialize module outside of this class loader");
        if (this.module != null || this.moduleInit != null)
            throw new IllegalArgumentException("Module already initialized!", this.moduleState);

        javaModule.initialize(this.loader, this.description, this.file, this);
        this.moduleState = new IllegalStateException("Initial initialization");
        this.moduleInit = javaModule;
    }
}