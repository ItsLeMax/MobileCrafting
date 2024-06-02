package de.max.mobilecrafting.events;

import de.max.mobilecrafting.inventories.GUI;
import de.max.mobilecrafting.inventories.Recipe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class PlayerInteract implements Listener {
    @EventHandler
    public static void playerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!Objects.equals(event.getItem(), Recipe.crafter)) return;

        GUI.loadInventory(event.getPlayer());
    }
}
