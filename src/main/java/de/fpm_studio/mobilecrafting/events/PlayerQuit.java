package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.MobileCrafting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public static void playerQuit(PlayerQuitEvent event) {
        MobileCrafting.playerCache.get(event.getPlayer().getUniqueId()).clear();
    }
}