package de.fpm_studio.mobilecrafting.service;

import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

/**
 * Holds HashMap caches
 *
 * @author ItsLeMax
 * @since Code: 1.0.0 <br> Class: 1.1.3
 */
@Getter
public final class CacheService {

    // The menu cache also serves as null check (if the player is cached)

    private final HashMap<UUID, Inventory> menuCache = new HashMap<>();

    private final HashMap<UUID, Inventory> craftingTableCache = new HashMap<>();
    private final HashMap<UUID, Inventory> furnaceCache = new HashMap<>();

}