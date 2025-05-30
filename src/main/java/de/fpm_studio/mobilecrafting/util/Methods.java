package de.fpm_studio.mobilecrafting.util;

import de.fpm_studio.mobilecrafting.MobileCrafting;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.UUID;

import static de.fpm_studio.mobilecrafting.MobileCrafting.configLib;

public class Methods {
    /**
     * Erstellt den Inventarcache
     * <p>
     * Creates the inventory cache
     *
     * @author ItsLeMax
     */
    public static void createCache(UUID uuid) {
        MobileCrafting.playerCache.put(uuid, new HashMap<>());
        MobileCrafting.playerCache.get(uuid).put("MENU", Bukkit.createInventory(null, 9, "§c" + configLib.lang("interface.mobileCraftingName")));
        MobileCrafting.playerCache.get(uuid).put("WORKBENCH", Bukkit.createInventory(null, InventoryType.WORKBENCH, "§c" + configLib.lang("interface.workbenchTitle")));
        MobileCrafting.playerCache.get(uuid).put("FURNACE", Bukkit.createInventory(null, InventoryType.FURNACE, "§5" + configLib.lang("interface.furnaceTitle")));
    }

    /**
     * Sendet den Cache in Intervallen in die Konsole
     * <p>
     * Sends the cache in intervals into the console
     *
     * @author ItsLeMax
     */
    @SuppressWarnings("unused")
    private void log() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MobileCrafting.plugin, () -> Bukkit.getConsoleSender().sendMessage("§8" + MobileCrafting.playerCache), 0, 200);
    }
}