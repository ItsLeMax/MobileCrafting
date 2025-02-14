package de.max.mobilecrafting.events;

import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public static void playerQuit(PlayerQuitEvent event) {
        MobileCrafting.playerCache.get(event.getPlayer().getUniqueId()).clear();
    }
}