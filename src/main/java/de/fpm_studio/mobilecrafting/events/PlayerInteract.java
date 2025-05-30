package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.inventories.GUI;
import de.fpm_studio.mobilecrafting.inventories.Recipe;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteract implements Listener {
    @EventHandler
    public static void playerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (event.getItem() != null && !event.getItem().equals(Recipe.crafter)) {
            return;
        }

        Player player = event.getPlayer();
        GUI.loadInventory(player);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
    }
}