package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.service.CacheService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Contains the player quit cache clear
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
public final class PlayerQuit implements Listener {

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        CacheService.playerCache.get(event.getPlayer().getUniqueId()).clear();
    }

}