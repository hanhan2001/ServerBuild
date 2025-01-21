package me.xiaoying.serverbuild.gui;

import me.xiaoying.serverbuild.core.SBPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class GuiListener implements Listener {
    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Gui gui;
        if ((gui = SBPlugin.getGuiManager().getCacheGui(event.getInventory().getHolder())) == null)
            return;

        event.setCancelled(true);

        int slot = event.getSlot();

        int x = slot % 9;
        int y = slot / 9;

        Component component = gui.getComponent(x, y);

        if (component == null)
            return;

        component.onClick();

        if (!component.needClose())
            return;

        event.getWhoClicked().closeInventory();
    }

    @EventHandler
    public void onPlayerInteractInventory(InventoryInteractEvent event) {
        if (SBPlugin.getGuiManager().getCacheGui(event.getInventory().getHolder()) == null)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMoveItemEvent(InventoryMoveItemEvent event) {
        if (SBPlugin.getGuiManager().getCacheGui(event.getInitiator().getHolder()) == null)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerOpenInventory(InventoryOpenEvent event) {
        Gui gui;
        if ((gui = SBPlugin.getGuiManager().getCacheGui(event.getInventory().getHolder())) == null)
            return;

        gui.open(Bukkit.getPlayer(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onPlayerPutInventory(InventoryCloseEvent event) {
        Gui gui;
        if ((gui = SBPlugin.getGuiManager().getCacheGui(event.getInventory().getHolder())) == null)
            return;

        gui.close(Bukkit.getPlayer(event.getPlayer().getUniqueId()));
        SBPlugin.getGuiManager().removeCacheGui(event.getInventory().getHolder());
    }
}