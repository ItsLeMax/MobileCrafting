package de.max.mobilecrafting.events;

import de.max.mobilecrafting.init.Methods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        Methods.createCache(event.getPlayer().getUniqueId());
    }
}