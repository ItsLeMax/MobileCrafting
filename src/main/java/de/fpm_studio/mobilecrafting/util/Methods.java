package de.fpm_studio.mobilecrafting.util;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.mobilecrafting.MobileCrafting;
import de.fpm_studio.mobilecrafting.service.CacheService;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Holds different utility methods
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class Methods {

    private final MobileCrafting instance;

    /**
     * Creates the inventory cache
     *
     * @param uuid UUID of the player to cache
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void createCache(@NotNull final UUID uuid) {

        final ConfigLib configLib = instance.getConfigLib();
        final CacheService cacheService = instance.getCacheService();

        // Creating all different custom / player related GUIs

        final Inventory menu = Bukkit.createInventory(null, 9,
                "§c" + configLib.text("interface.mobileCraftingName")
        );

        final Inventory craftingTable = Bukkit.createInventory(null, InventoryType.CRAFTING,
                "§c" + configLib.text("interface.craftingTableTitle")
        );
        final Inventory furnace = Bukkit.createInventory(null, InventoryType.FURNACE,
                "§5" + configLib.text("interface.furnaceTitle")
        );

        // Putting them into the cache of the player

        cacheService.getMenuCache().put(uuid, menu);

        cacheService.getCraftingTableCache().put(uuid, craftingTable);
        cacheService.getFurnaceCache().put(uuid, furnace);

    }

}