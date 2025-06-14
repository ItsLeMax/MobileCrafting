package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.MobileCrafting;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Contains the block place event to cancel placing the mobile crafter item
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class BlockPlace implements Listener {

    private final MobileCrafting instance;

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {

        if (!event.getItemInHand().equals(instance.getRecipe().getCrafter()))
            return;

        event.setCancelled(true);

    }

}