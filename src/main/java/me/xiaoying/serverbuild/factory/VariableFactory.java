package me.xiaoying.serverbuild.factory;

import me.clip.placeholderapi.PlaceholderAPI;
import me.xiaoying.serverbuild.utils.ColorUtil;
import me.xiaoying.serverbuild.utils.DateUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * VariableFactory
 */
public class VariableFactory {
    String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory date(String dateFormat) {
        this.string = this.string.replace("%date%", DateUtil.getDate(dateFormat));
        return this;
    }

    public VariableFactory placeholder(Player player) {
        this.string = PlaceholderAPI.setPlaceholders(player, this.string);
        return this;
    }

    public VariableFactory player(String player) {
        this.string = this.string.replace("%player%", player);
        return this;
    }

    public VariableFactory player(Player player) {
        this.string = this.string.replace("%player%", player.getName());
        return this;
    }

    public VariableFactory prefix(String prefix) {
        this.string = this.string.replace("%prefix%", prefix);
        return this;
    }

    public VariableFactory target(String target) {
        this.string = this.string.replace("%target%", target);
        return this;
    }

    public VariableFactory target(Entity entity) {
        this.string = this.string.replace("%target%", entity.getName());
        return this;
    }

    public VariableFactory color() {
        this.string = ColorUtil.translate(this.string);
        return this;
    }

    public String getString() {
        return this.string;
    }

    @Override
    public String toString() {
        return this.string;
    }
}