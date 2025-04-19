package me.xiaoying.serverbuild.factory;

import me.clip.placeholderapi.PlaceholderAPI;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.module.JavaModule;
import me.xiaoying.serverbuild.module.Module;
import me.xiaoying.serverbuild.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Factory Variable
 */
public class VariableFactory {
    protected String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory amount(int amount) {
        this.string = this.string.replace("%amount%", String.valueOf(amount));
        return this;
    }

    public VariableFactory amount(String amount) {
        this.string = this.string.replace("%amount%", amount);
        return this;
    }

    public VariableFactory chunks(int chunks) {
        this.string = this.string.replace("%chunks%", String.valueOf(chunks));
        return this;
    }

    public VariableFactory entities(int entities) {
        this.string = this.string.replace("%entities%", String.valueOf(entities));
        return this;
    }

    public VariableFactory color() {
        this.string = ColorUtil.translate(this.string);
        return this;
    }

    public VariableFactory command(String command) {
        this.string = this.string.replace("%command%", command);
        return this;
    }

    public VariableFactory command_alias(String alias) {
        this.string = this.string.replace("%command_aliases%", alias);
        return this;
    }

    public VariableFactory command_alias(List<String> alias) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < alias.size(); i++) {
            stringBuilder.append(alias.get(i));

            if (i == alias.size() - 1)
                break;

            stringBuilder.append(", ");
        }
        this.string = this.string.replace("%command_aliases%", stringBuilder.toString());
        return this;
    }

    public VariableFactory command_usage(List<String> usage) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < usage.size(); i++) {
            stringBuilder.append(usage.get(i));

            if (i == usage.size() - 1)
                break;

            stringBuilder.append("\n");
        }

        this.command_usage(stringBuilder.toString());
        return this;
    }

    public VariableFactory command_usage(String usage) {
        this.string = this.string.replace("%command_usage%", usage);
        return this;
    }

    public VariableFactory date(String format) {
        this.string = this.string.replace("%date%", new SimpleDateFormat(format).format(new Date()));
        return this;
    }

    public VariableFactory description(String description) {
        this.string = this.string.replace("%description%", description);
        return this;
    }

    public VariableFactory message(String message) {
        this.string = this.string.replace("%message%", message);
        return this;
    }

    public VariableFactory module(Module module) {
        return this.module(module.getAliasName());
    }

    public VariableFactory module(String module) {
        this.string = this.string.replace("%module%", module);
        return  this;
    }

    public VariableFactory module_exact(Module module) {
        this.string = this.string.replace("%module_exact%", module.getName());
        return this;
    }

    public VariableFactory module_info_author(Module module) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> authors;
        if (module instanceof JavaModule)
            authors = ((JavaModule) module).getDescription().getAuthors();
        else
            authors = SBPlugin.getInstance().getDescription().getAuthors();

        for (int i = 0; i < authors.size(); i++) {
            stringBuilder.append(authors.get(i));

            if (i == authors.size() - 1)
                break;

            stringBuilder.append(", ");
        }

        this.string = this.string.replace("%module_info_author%", stringBuilder.toString());
        return this;
    }

    public VariableFactory module_info_version(Module module) {
        String version;
        if (module instanceof JavaModule)
            version = ((JavaModule) module).getDescription().getVersion();
        else
            version = SBPlugin.getInstance().getDescription().getVersion();

        this.string = this.string.replace("%module_info_version%", version);
        return this;
    }

    public VariableFactory module_state_enable(Module module) {
        this.string = this.string.replace("%module_state_enable%", String.valueOf(module.isEnabled()));
        return this;
    }

    public VariableFactory module_state_enable(boolean enable) {
        this.string = this.string.replace("%module_state_enable%", String.valueOf(enable));
        return this;
    }

    public VariableFactory module_state_ready(Module module) {
        this.string = this.string.replace("%module_state_ready%", String.valueOf(module.ready()));
        return this;
    }

    public VariableFactory module_state_ready(boolean ready) {
        this.string = this.string.replace("%module_state_ready%", String.valueOf(ready));
        return this;
    }

    public VariableFactory parameter(String parameter) {
        this.string = this.string.replace("%parameter%", parameter);
        return this;
    }

    public VariableFactory placeholder(CommandSender sender) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
            return this;

        if (!(sender instanceof Player))
            return this;

        this.string = PlaceholderAPI.setPlaceholders((Player) sender, this.string);
        return this;
    }

    public VariableFactory player(Player player) {
        return this.player(player.getDisplayName());
    }

    public VariableFactory player(String player) {
        this.string = this.string.replace("%player%", player);
        return this;
    }

    public VariableFactory prefix(String prefix) {
        this.string = this.string.replace("%prefix%", prefix);
        return this;
    }

    public VariableFactory server_bukkit_version(String bukkit_version) {
        this.string = this.string.replace("%server_bukkit_version%", bukkit_version);
        return this;
    }

    public VariableFactory server_tps_1m(String tps) {
        this.string = this.string.replace("%server_tps_1m%", tps);
        return this;
    }

    public VariableFactory server_tps_5m(String tps) {
        this.string = this.string.replace("%server_tps_5m%", tps);
        return this;
    }

    public VariableFactory server_tps_15m(String tps) {
        this.string = this.string.replace("%server_tps_15m%", tps);
        return this;
    }

    public VariableFactory server_whitelist_enabled(boolean enabled) {
        this.string = this.string.replace("%server_whitelist_enabled%", String.valueOf(enabled));
        return this;
    }

    public VariableFactory server_memory_max(String max) {
        this.string = this.string.replace("%server_memory_max%", max);
        return this;
    }

    public VariableFactory server_memory_now(String now) {
        this.string = this.string.replace("%server_memory_now%", now);
        return this;
    }

    public VariableFactory server_memory_idle(String idle) {
        this.string = this.string.replace("%server_memory_idle%", idle);
        return this;
    }

    public VariableFactory server_version(String version) {
        this.string = this.string.replace("%server_version%", version);
        return this;
    }

    public VariableFactory server_world_status(String world_status) {
        this.string = this.string.replace("%server_worlds_status%", world_status);
        return this;
    }

    public VariableFactory server_world_tile_block(int amount) {
        this.string = this.string.replace("%server_world_tile_block%", String.valueOf(amount));
        return this;
    }

    public VariableFactory time(int time) {
        return this.time(String.valueOf(time));
    }

    public VariableFactory time(long time) {
        return this.time(String.valueOf(time));
    }

    public VariableFactory time(String time) {
        this.string = this.string.replace("%time%", time);
        return this;
    }

    public VariableFactory world(String world) {
        this.string = this.string.replace("%world%", world);
        return this;
    }

    public VariableFactory world(World world) {
        return this.world(world.getName());
    }

    @Override
    public String toString() {
        this.string = this.string.replace("\\r", " ");
        this.string = this.string.replace("\\n", "\n");
        return this.string;
    }
}