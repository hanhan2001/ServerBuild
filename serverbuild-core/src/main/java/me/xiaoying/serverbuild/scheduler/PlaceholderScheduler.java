package me.xiaoying.serverbuild.scheduler;

import me.clip.placeholderapi.PlaceholderAPI;
import me.xiaoying.serverbuild.core.SBPlugin;
import org.bukkit.Bukkit;

/**
 * Scheduler to check placeholder is enabled in PlaceholderAPI
 */
public class PlaceholderScheduler extends Scheduler {
    public PlaceholderScheduler() {
        super(Type.SYNC_REPEAT, 20L);
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            // determine PlaceholderAPI is loaded
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
                return;

            // determine PlaceholderModule is enabled in PlaceholderAPI
            SBPlugin.getModuleManager().getModules().forEach(module -> {
                if (!module.isEnabled())
                    return;

                module.getPlaceholders().forEach(placeholderModule -> {
                    if (PlaceholderAPI.getRegisteredIdentifiers().contains(placeholderModule.getIdentifier()))
                        return;

                    placeholderModule.register();
                });
            });
        };
    }
}