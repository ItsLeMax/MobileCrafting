package de.max.mobilecrafting.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;
import static de.max.mobilecrafting.init.MobileCrafting.playerCache;

public class InventoryClose implements Listener {
    @EventHandler
    public static void inventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        UUID uuid = event.getPlayer().getUniqueId();

        for (String craftGUI : new String[]{"FURNACE", "WORKBENCH"}) {
            if (!inventory.equals(playerCache.get(uuid).get(craftGUI))) {
                continue;
            }

            for (int slot = 0; slot < inventory.getType().getDefaultSize(); slot++) {
                configLib.getConfig("storage").set(uuid + ".Inventory." + inventory.getType() + "." + slot, inventory.getItem(slot));
            }

            configLib.save("storage");
        }
    }
}