package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.mobilecrafting.MobileCrafting;
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

    private final MobileCrafting instance;

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {

        final ConfigLib configLib = instance.getConfigLib();
        final CacheService cacheService = instance.getCacheService();

        final Inventory inventory = event.getView().getTopInventory();
        final UUID uuid = event.getPlayer().getUniqueId();

        // Event only meant for custom guis

        if (inventory.equals(cacheService.getMenuCache().get(uuid)))
            return;

        // Storing each item of every slot of the previously opened gui

        for (int slot = 0; slot < inventory.getType().getDefaultSize(); slot++) {

            if (inventory.getItem(slot) == null)
                continue;

            final ItemStack item = inventory.getItem(slot);
            assert item != null;

            configLib.getConfig("storage").set(uuid + ".inventory."
                    + inventory.getType().toString().toLowerCase() + "." + slot, item
            );

        }

        configLib.saveConfig("storage");

    }

}