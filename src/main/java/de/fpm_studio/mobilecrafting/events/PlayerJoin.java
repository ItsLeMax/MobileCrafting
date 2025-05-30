package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.util.Methods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        Methods.createCache(event.getPlayer().getUniqueId());
    }
}