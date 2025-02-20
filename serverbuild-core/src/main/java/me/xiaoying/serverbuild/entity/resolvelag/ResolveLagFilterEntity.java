package me.xiaoying.serverbuild.entity.resolvelag;

import me.xiaoying.serverbuild.utils.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Locale;

public class ResolveLagFilterEntity {
    private final String type;
    private final List<String> values;
    private final int exclude;

    public ResolveLagFilterEntity(String type, List<String> values, int exclude) {
        this.type = type;
        this.values = values;
        this.exclude = exclude;
    }

    public String getType() {
        return this.type;
    }

    public List<String> getValues() {
        return this.values;
    }

    public int getExclude() {
        return this.exclude;
    }

    public boolean match(Entity entity) {
        switch (this.getType().toUpperCase(Locale.ENGLISH)) {
            case "ENUM": {
                String type;
                if (entity instanceof Item)
                    type = ((Item) entity).getItemStack().getType().toString();
                else
                    type = entity.getType().toString();

                for (String value : this.getValues()) {
                    if (!StringUtil.match(value, type, 0, 0))
                        continue;

                    return true;
                }
                break;
            }
            case "NAME":
                if (entity.getCustomName() == null || entity.getCustomName().isEmpty())
                    return false;

                for (String value : this.getValues()) {
                    if (!StringUtil.match(value, entity.getCustomName(), 0, 0))
                        continue;

                    return true;
                }
                break;
            case "TYPE":
                String type = null;
                if (entity instanceof Item)
                    type = "ITEM";
                else if (entity instanceof Animals)
                    type = "ANIMALS";
                else if (entity instanceof Monster)
                    type = "MONSTER";
                else if (entity instanceof Player)
                    type = "PLAYER";
                else if (entity instanceof HumanEntity)
                    type = "HUMAN_ENTITY";
                else if (entity instanceof LivingEntity)
                    type = "LIVING_ENTITY";

                if (type == null)
                    return false;

                for (String value : this.getValues()) {
                    if (!StringUtil.match(value, type, 0, 0))
                        continue;

                    return true;
                }
                return false;
            case "LORE":
                if (!(entity instanceof Item))
                    return false;

                Item item = (Item) entity;
                ItemMeta itemMeta = item.getItemStack().getItemMeta();
                if (itemMeta == null)
                    return false;

                if (itemMeta.getLore() == null)
                    return false;

                for (String s : itemMeta.getLore()) {
                    for (String value : this.getValues()) {
                        if (!StringUtil.match(value, ChatColor.stripColor(s), 0, 0))
                            continue;

                        return true;
                    }
                }
                break;
            case "POSE":
                if (!(entity instanceof LivingEntity))
                    return false;

                for (String value : this.getValues()) {
                    if (!StringUtil.match(value, entity.getPose().toString(), 0, 0))
                        continue;

                    return true;
                }

                return false;
            case "CLASS":
                for (String value : this.getValues()) {
                    if (!StringUtil.match(value, entity.getClass().getName(), 0, 0))
                        continue;

                    return true;
                }
                return false;
            case "WORLD":
                for (String value : this.getValues()) {
                    if (!StringUtil.match(value, entity.getWorld().getName(), 0, 0))
                        continue;

                    return true;
                }
                return false;
            case "METADATA":
                break;
            case "ENCHANTMENT":
                if (!(entity instanceof Item))
                    return false;

                ItemStack itemStack = ((Item) entity).getItemStack();
                for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                    for (String value : this.getValues()) {
                        if (!value.contains(":"))
                            value = "minecraft:" + value;

                        if (!StringUtil.match(value, enchantment.getKey().toString(), 0, 0))
                            continue;

                        return true;
                    }
                }
            default:
                return false;
        }
        return false;
    }
}