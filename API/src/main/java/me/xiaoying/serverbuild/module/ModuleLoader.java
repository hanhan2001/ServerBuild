package me.xiaoying.serverbuild.module;

import java.io.File;
import java.util.regex.Pattern;

public interface ModuleLoader {
    JavaModule loadModule(File file) throws InvalidModuleException;

    ModuleDescription getModuleDescription(File file) throws InvalidDescriptionException;

    Pattern[] getPluginFileFilters();

    void enableModule(JavaModule module);

    void disableModule(JavaModule module);
}