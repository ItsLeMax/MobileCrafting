package de.fpm_studio.mobilecrafting.listener;

import de.fpm_studio.mobilecrafting.MobileCrafting;
import de.fpm_studio.mobilecrafting.service.CacheService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Contains the player quit cache clear
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class PlayerQuitListener implements Listener {

    private final MobileCrafting instance;

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {

        final CacheService cacheService = instance.getCacheService();

        cacheService.getMenuCache().remove(event.getPlayer().getUniqueId());

        cacheService.getCraftingTableCache().remove(event.getPlayer().getUniqueId());
        cacheService.getFurnaceCache().remove(event.getPlayer().getUniqueId());

    }

}