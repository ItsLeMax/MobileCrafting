package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.inventories.Recipe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    @EventHandler
    public static void blockPlace(BlockPlaceEvent event) {
        if (!event.getItemInHand().equals(Recipe.crafter)) return;
        event.setCancelled(true);
    }
}