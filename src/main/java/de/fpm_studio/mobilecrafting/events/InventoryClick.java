package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.mobilecrafting.MobileCrafting;
import de.fpm_studio.mobilecrafting.inventories.MobileCrafterGUI;
import de.fpm_studio.mobilecrafting.service.CacheService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Handles the inventory clicks of everything plugin related
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class InventoryClick implements Listener {

    private final MobileCrafting instance;

    private final CacheService cacheService = instance.getCacheService();
    private final MobileCrafterGUI mobileCrafterGUI = instance.getMobileCrafterGui();

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {

        final ItemStack clickedItem = event.getCurrentItem();
        final Inventory clickedInventory = event.getClickedInventory();

        if (clickedItem == null || clickedInventory == null)
            return;

        final Player player = (Player) event.getView().getPlayer();
        final UUID uuid = player.getUniqueId();

        // Menu interaction handling

        if (!clickedInventory.equals(cacheService.getMenuCache().get(uuid)))
            return;

        event.setCancelled(true);

        mobileCrafterGUI.onMenuInteraction(event);

    }

}