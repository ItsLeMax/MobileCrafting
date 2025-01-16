package de.max.mobilecrafting.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;
import static de.max.mobilecrafting.init.MobileCrafting.playerCache;

public class InventoryClose implements Listener {
    @EventHandler
    public static void inventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (clickedInventoryEquals(event, "FURNACE", "WORKBENCH")) {
            for (int index = 0; index < inventory.getType().getDefaultSize(); index++) {
                configLib.getConfig("storage").set(event.getView().getPlayer().getUniqueId() + ".Inventory." + inventory.getType() + "." + index, inventory.getItem(index));
            }

            configLib.save("storage");
        }
    }

    /**
     * Überprüft, ob es sich um ein individuelles Inventar handelt, in dem geklickt wurde
     * <p>
     * Checks wheter an inventory is individual in which has been clicked
     *
     * @param GUIs GUIs, in welchen geklickt wurde
     *             <p>
     *             GUIs in which has been clicked
     * @return Boolean, ob individuelles Inventar
     * @author ItsLeMax
     */
    private static boolean clickedInventoryEquals(InventoryCloseEvent event, String... GUIs) {
        for (String GUI : GUIs) {
            if (Objects.equals(event.getInventory(), playerCache.get(event.getPlayer().getUniqueId()).get(GUI))) {
                return true;
            }
        }

        return false;
    }
}