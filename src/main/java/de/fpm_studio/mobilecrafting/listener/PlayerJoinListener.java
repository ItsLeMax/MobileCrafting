package de.fpm_studio.mobilecrafting.listener;

import de.fpm_studio.mobilecrafting.MobileCrafting;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Contains the player join cache creation
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class PlayerJoinListener implements Listener {

    private final MobileCrafting instance;

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        instance.getUtilityHandler().createCache(event.getPlayer().getUniqueId());
    }

}