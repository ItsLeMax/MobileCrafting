package de.fpm_studio.mobilecrafting.util;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.CraftingTableGui;
import com.github.stefvanschie.inventoryframework.gui.type.FurnaceGui;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.mobilecrafting.data.CustomInventoryType;
import de.fpm_studio.mobilecrafting.service.CacheService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

/**
 * Holds different utility methods
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class Methods {

    private final ConfigLib configLib;

    private final CacheService cacheService;

    /**
     * Creates the inventory cache
     *
     * @param uuid UUID of the player to cache
     * @author ItsLeMax
     */
    public void createCache(@NotNull final UUID uuid) {

        cacheService.getPlayerCache().put(uuid, new HashMap<>());

        // Creating all different custom / player related GUIs

        final ChestGui menu = new ChestGui(1, "§c" + configLib.text("interface.mobileCraftingName"));
        final CraftingTableGui craftingTable = new CraftingTableGui("§c" + configLib.text("interface.workbenchTitle"));
        final FurnaceGui furnace = new FurnaceGui("§5" + configLib.text("interface.furnaceTitle"));

        // Putting them into the cache of the player

        cacheService.getPlayerCache().get(uuid).put(CustomInventoryType.MENU, menu);
        cacheService.getPlayerCache().get(uuid).put(CustomInventoryType.WORKBENCH, craftingTable);
        cacheService.getPlayerCache().get(uuid).put(CustomInventoryType.FURNACE, furnace);

    }

}