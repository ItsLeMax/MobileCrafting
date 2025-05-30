package de.fpm_studio.mobilecrafting.service;

import de.fpm_studio.mobilecrafting.data.CustomInventoryType;

import java.util.HashMap;
import java.util.UUID;

/**
 * Holds HashMap caches
 *
 * @author ItsLeMax
 * @since Code: 1.0.0 <br> Class: 1.1.3
 */
public final class CacheService {
    public static final HashMap<UUID, HashMap<CustomInventoryType, Object>> playerCache = new HashMap<>();
}