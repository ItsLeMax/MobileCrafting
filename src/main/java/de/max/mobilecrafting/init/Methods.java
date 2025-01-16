package de.max.mobilecrafting.init;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.UUID;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;
import static de.max.mobilecrafting.init.MobileCrafting.playerCache;

public class Methods {
    /**
     * Erstellt den Inventarcache
     * <p>
     * Creates the inventory cache
     *
     * @author ItsLeMax
     */
    public static void createCache(UUID uuid) {
        playerCache.put(uuid, new HashMap<>());
        playerCache.get(uuid).put("MENU", Bukkit.createInventory(null, 9, "§c" + configLib.lang("interface.mobileCraftingName")));
        playerCache.get(uuid).put("WORKBENCH", Bukkit.createInventory(null, InventoryType.WORKBENCH, "§c" + configLib.lang("interface.workbenchTitle")));
        playerCache.get(uuid).put("FURNACE", Bukkit.createInventory(null, InventoryType.FURNACE, "§5" + configLib.lang("interface.furnaceTitle")));
    }
}