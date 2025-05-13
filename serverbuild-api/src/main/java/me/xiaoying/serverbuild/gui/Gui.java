package me.xiaoying.serverbuild.gui;

import me.xiaoying.serverbuild.core.SBPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 此处代码将进行重构，重写逻辑
 */
public abstract class Gui implements Cloneable {
    private String name;
    private String displayName;
    private List<Component> components = new ArrayList<>();
    private int height;

    private InventoryHolder holder;

    public Gui(String name) {
        this.name = name;
        this.displayName = name;
    }

    public void open(Player player) {
        this.onOpen(player);
        Inventory inventory = this.getInventory();
        player.openInventory(inventory);
        this.holder = inventory.getHolder();
        SBPlugin.getGuiManager().addCacheGui(this.holder, this);
    }

    public void close(Player player) {
        this.onClose(player);
        SBPlugin.getGuiManager().removeCacheGui(this.holder);
    }

    protected abstract void onOpen(Player player);
    protected abstract void onClose(Player player);

    public String getName() {
        return this.name;
    }

    /**
     * Get inventory display name
     *
     * @return String
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Set inventory display name
     *
     * @param displayName display name
     * @return Gui
     */
    public Gui setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get gui component by position
     *
     * @param x x-axis
     * @param y t-axis
     * @return Component
     */
    public Component getComponent(int x, int y) {
        for (Component component : this.components) {
            if (component.getX() != x || component.getY() != y)
                continue;

            return component;
        }
        return null;
    }

    /**
     * Get components
     *
     * @return ArrayList
     */
    public List<Component> getComponents() {
        return this.components;
    }

    /**
     * Add component to this gui
     *
     * @param component Component
     * @return Gui
     */
    public Gui addComponent(Component component) {
        this.components.add(component);
        return this;
    }

    /**
     * Remove component
     *
     * @param component  Component
     * @return Gui
     */
    public Gui removeComponent(Component component) {
        this.components.remove(component);
        return this;
    }

    /**
     * Remove component by position
     *
     * @param x x-axis
     * @param y y-axis
     * @return Gui
     */
    public Gui removeComponent(int x, int y) {
        Iterator<Component> iterator = this.components.iterator();
        Component component;
        while (iterator.hasNext()) {
            component = iterator.next();

            if (component.getX() != x || component.getY() != y)
                continue;

            this.components.remove(component);
        }
        return this;
    }

    /**
     * Get height of gui
     *
     * @return gui's height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set height of gui
     *
     * @param height gui's height
     */
    public void setHeight(int height) {
        if (height < 0 || height > 6)
            throw new RuntimeException(new IllegalArgumentException("GUI height need between 1 and 6."));

        this.height = height;
    }

    /**
     * Get inventory of gui
     *
     * @return Inventory
     */
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, this.height * 9, this.getDisplayName());
        this.components.forEach(component -> {
            if (component.getY() > 5 || component.getY() > 8)
                return;

            inventory.setItem(component.getY() * 9 + component.getX(), component.getItemStack());
        });
        return inventory;
    }

    public void click(InventoryClickEvent event) {
        event.setCancelled(true);

        int slot = event.getRawSlot();

        int x = slot % 9;
        int y = slot / 9;

        Component component = this.getComponent(x, y);

        if (component == null)
            return;

        component.onClick();

        if (!component.needClose())
            return;

        event.getWhoClicked().closeInventory();
    }

    public void interact(InventoryInteractEvent event) {
        if (SBPlugin.getGuiManager().getCacheGui(event.getInventory().getHolder()) == null)
            return;

        event.setCancelled(true);
    }

    public void moveItem(InventoryMoveItemEvent event) {

        if (SBPlugin.getGuiManager().getCacheGui(event.getInitiator().getHolder()) == null)
            return;

        event.setCancelled(true);
    }

    public Gui clone() {
        try {
            Gui clone = (Gui) super.clone();
            clone.components = new ArrayList<>(this.components);
            return clone;
        } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
        }
    }

    public <T extends Gui> T backup() {
        return (T) this.clone();
    }
}