package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.util.Methods;
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
public final class PlayerJoin implements Listener {

    private final Methods methods;

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        methods.createCache(event.getPlayer().getUniqueId());
    }

}