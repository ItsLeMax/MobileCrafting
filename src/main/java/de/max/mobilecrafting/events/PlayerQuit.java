package de.max.mobilecrafting.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static de.max.mobilecrafting.init.MobileCrafting.playerCache;

public class PlayerQuit implements Listener {
    @EventHandler
    public static void playerQuit(PlayerQuitEvent event) {
        playerCache.get(event.getPlayer().getUniqueId()).clear();
    }
}