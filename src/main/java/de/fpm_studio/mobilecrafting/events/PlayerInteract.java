package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.inventories.MobileCrafterGUI;
import de.fpm_studio.mobilecrafting.inventories.Recipe;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Contains the player interaction with the mobile crafter item
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class PlayerInteract implements Listener {

    private final Recipe recipe;
    private final MobileCrafterGUI mobileCrafterGui;

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        // Open the crafter on interaction

        if (event.getHand() != EquipmentSlot.HAND)
            return;

        if (event.getItem() != null && !event.getItem().equals(recipe.getCrafter()))
            return;

        final Player player = event.getPlayer();

        mobileCrafterGui.loadInventory(player);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);

    }

}