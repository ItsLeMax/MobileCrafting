package de.max.mobilecrafting.events;

import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;

public class InventoryClose implements Listener {
    @EventHandler
    public static void inventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        UUID uuid = event.getPlayer().getUniqueId();

        for (String craftingTable : new String[]{"WORKBENCH", "FURNACE"}) {
            if (!inventory.equals(MobileCrafting.playerCache.get(uuid).get(craftingTable))) {
                continue;
            }

            for (int slot = 0; slot < inventory.getType().getDefaultSize(); slot++) {
                if (inventory.getItem(slot) == null) {
                    continue;
                }

                ItemStack item = inventory.getItem(slot);
                assert item != null;

                if (item.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                    continue;
                }

                configLib.getConfig("storage").set(uuid + ".Inventory." + inventory.getType() + "." + slot, item);
            }

            configLib.save("storage");
            break;
        }
    }
}