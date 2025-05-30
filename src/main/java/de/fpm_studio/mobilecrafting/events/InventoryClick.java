package de.fpm_studio.mobilecrafting.events;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.mobilecrafting.data.CustomInventoryType;
import de.fpm_studio.mobilecrafting.service.CacheService;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
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

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {

        final ItemStack clickedItem = event.getCurrentItem();
        final Inventory clickedInventory = event.getClickedInventory();

        if (clickedItem == null || clickedInventory == null)
            return;

        final Player player = (Player) event.getView().getPlayer();
        final UUID uuid = player.getUniqueId();

        // Fix of a duplication issue

        if (event.getInventory().equals(CacheService.playerCache.get(uuid).get(CustomInventoryType.WORKBENCH)) && event.isShiftClick()) {

            if (!clickedInventory.getType().equals(InventoryType.PLAYER))
                return;

            event.setCancelled(true);

        }

        // Menu interaction handling

        if (!clickedInventory.equals(CacheService.playerCache.get(uuid).get(CustomInventoryType.MENU)))
            return;

        event.setCancelled(true);

        if (clickedItem.getType().toString().contains("STAINED_GLASS_PANE"))
            return;

        // Opening each menu on click interaction

        CustomInventoryType subMenuType = switch (clickedItem.getType().toString()) {
            case "CRAFTING_TABLE" -> CustomInventoryType.WORKBENCH;
            default -> CustomInventoryType.valueOf(clickedInventory.getType().toString());
        };

        final Inventory subMenu = ((Inventory) CacheService.playerCache.get(uuid).get(subMenuType));
        player.openInventory(subMenu);

        // QoL

        final Sound sound = switch (clickedItem.getType()) {
            case CRAFTING_TABLE -> Sound.BLOCK_WOOD_PLACE;
            case FURNACE -> Sound.ITEM_FIRECHARGE_USE;
            default -> throw new RuntimeException();
        };

        player.playSound(player.getLocation(), sound, 1, 1);

        // Load the stored items of the custom gui

        final FileConfiguration storage = configLib.getConfig("storage");

        for (int slot = 0; slot < subMenu.getType().getDefaultSize(); slot++) {

            final String path = uuid + ".Inventory." + subMenuType + "." + slot;

            if (storage.get(path) == null)
                continue;

            subMenu.setItem(slot, (ItemStack) storage.get(path));

        }
    }

}