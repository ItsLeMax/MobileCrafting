package de.fpm_studio.mobilecrafting.listener;

import de.fpm_studio.mobilecrafting.MobileCrafting;
import de.fpm_studio.mobilecrafting.inventories.MobileCrafterGUI;
import de.fpm_studio.mobilecrafting.registry.ItemRegistry;
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
public final class PlayerInteractListener implements Listener {

    private final MobileCrafting instance;

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        final ItemRegistry itemRegistry = instance.getItemRegistry();
        final MobileCrafterGUI mobileCrafterGui = instance.getMobileCrafterGui();

        // Open the crafter on interaction

        if (event.getHand() != EquipmentSlot.HAND)
            return;

        if (event.getItem() != null && !event.getItem().equals(itemRegistry.getCrafter()))
            return;

        final Player player = event.getPlayer();

        mobileCrafterGui.loadInventory(player);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);

    }

}