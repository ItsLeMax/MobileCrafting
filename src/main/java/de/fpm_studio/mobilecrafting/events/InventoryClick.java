package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.mobilecrafting.service.CacheService;
import de.fpm_studio.mobilecrafting.util.Methods;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
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

    private final ConfigLib configLib;
    private final CacheService cacheService;

    private final Methods methods;

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

        if (clickedItem.getType().toString().contains("STAINED_GLASS_PANE"))
            return;

        // Opening each menu on click interaction

        final Inventory openedSubMenu = switch (clickedItem.getType()) {

            case CRAFTING_TABLE -> cacheService.getCraftingTableCache().get(uuid);
            case FURNACE -> cacheService.getFurnaceCache().get(uuid);

            default -> {
                methods.createCache(uuid);

                throw new NullPointerException("Cache creation failed for " + player.getName() + " (" + uuid + ") - " +
                        "Inventory could not be opened - New attempt in progress..."
                );
            }

        };

        // QoL

        final Sound sound = switch (clickedItem.getType()) {

            case CRAFTING_TABLE -> Sound.BLOCK_WOOD_PLACE;
            case FURNACE -> Sound.ITEM_FIRECHARGE_USE;

            default -> throw new RuntimeException();

        };

        player.playSound(player.getLocation(), sound, 1, 1);

        // Load the stored items of the custom gui

        final FileConfiguration storage = configLib.getConfig("storage");

        for (int slot = 0; slot < openedSubMenu.getType().getDefaultSize(); slot++) {
            final String path = uuid + ".inventory." + openedSubMenu.getType().toString().toLowerCase() + "." + slot;

            if (storage.get(path) == null)
                continue;

            openedSubMenu.setItem(slot, (ItemStack) storage.get(path));
        }

    }

}