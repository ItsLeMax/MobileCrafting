package de.max.mobilecrafting.init;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.UUID;

public class Methods {
    /**
     * Erstellt den Inventarcache
     *
     * @author ItsLeMax
     */
    public static void createCache(UUID uuid) {
        MobileCrafting.playerCache.put(uuid, new HashMap<>());
        MobileCrafting.playerCache.get(uuid).put("Menu", Bukkit.createInventory(null, 9));
        MobileCrafting.playerCache.get(uuid).put("Workbench", Bukkit.createInventory(null, InventoryType.WORKBENCH));
        MobileCrafting.playerCache.get(uuid).put("Furnace", Bukkit.createInventory(null, InventoryType.FURNACE));
    }
}
