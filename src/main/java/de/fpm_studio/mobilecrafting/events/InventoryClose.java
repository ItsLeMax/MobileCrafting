package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.mobilecrafting.data.CustomInventoryType;
import de.fpm_studio.mobilecrafting.service.CacheService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Holds code about saving items put into the menus
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class InventoryClose implements Listener {

    private final ConfigLib configLib;
    private final CacheService cacheService;

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {

        final Inventory inventory = event.getInventory();
        final UUID uuid = event.getPlayer().getUniqueId();

        // Storing the items of the previously opened gui

        for (final CustomInventoryType craftingTable : new CustomInventoryType[]{
                CustomInventoryType.WORKBENCH,
                CustomInventoryType.FURNACE
        }) {

            if (!inventory.equals(cacheService.getPlayerCache().get(uuid).get(craftingTable)))
                continue;

            // Each item of every slot

            for (int slot = 0; slot < inventory.getType().getDefaultSize(); slot++) {

                if (inventory.getItem(slot) == null)
                    continue;

                final ItemStack item = inventory.getItem(slot);
                assert item != null;

                configLib.getConfig("storage").set(uuid + ".Inventory." + inventory.getType() + "." + slot, item);

            }

            configLib.saveConfig("storage");
            break;

        }

    }

}