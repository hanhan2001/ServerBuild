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

        gui.click(event);
    }

    @EventHandler
    public void onPlayerInteractInventory(InventoryInteractEvent event) {
        Gui gui;
        if ((gui = SBPlugin.getGuiManager().getCacheGui(event.getInventory().getHolder())) == null)
            return;

        gui.interact(event);
    }

    @EventHandler
    public void onPlayerMoveItemEvent(InventoryMoveItemEvent event) {
        Gui gui;
        if ((gui = SBPlugin.getGuiManager().getCacheGui(event.getInitiator().getHolder())) == null)
            return;

        gui.moveItem(event);
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