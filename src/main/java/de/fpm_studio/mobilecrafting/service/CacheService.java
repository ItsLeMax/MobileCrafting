package de.fpm_studio.mobilecrafting.service;

import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Holds Map caches
 *
 * @author ItsLeMax
 * @since Code: 1.0.0 <br> Class: 1.1.3
 */
@Getter
public final class CacheService {

    // The menu cache also serves as null check (if the player is cached)

    private final Map<UUID, Inventory> menuCache = new HashMap<>();

    private final Map<UUID, Inventory> craftingTableCache = new HashMap<>();
    private final Map<UUID, Inventory> furnaceCache = new HashMap<>();

}