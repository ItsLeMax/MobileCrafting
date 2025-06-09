package de.fpm_studio.mobilecrafting.inventories;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.ItemLib;
import de.fpm_studio.mobilecrafting.MobileCrafting;
import de.fpm_studio.mobilecrafting.service.CacheService;
import de.fpm_studio.mobilecrafting.util.Methods;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Contains the gui of the mobile crafter
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class MobileCrafterGUI {

    private final MobileCrafting instance;

    /**
     * Loads the GUI of the menu
     *
     * @param player Player to load the gui for
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void loadInventory(@NotNull final Player player) {

        final ConfigLib configLib = instance.getConfigLib();
        final CacheService cacheService = instance.getCacheService();

        // Loading the player bound mobile crafter main menu...

        final Inventory menu = cacheService.getMenuCache().get(player.getUniqueId());

        player.openInventory(menu);

        // ...with its items

        menu.setItem(3, new ItemLib()
                .setItem(Material.CRAFTING_TABLE)
                .setName("§c" + configLib.text("interface.craftingTableTitle"))
                .create());

        // ...depending if they each are unlocked

        final boolean hasFurnace = configLib.getConfig("storage")
                .getBoolean(player.getUniqueId() + ".furnace.unlocked");

        final ItemLib furnaceBuilder = new ItemLib();

        furnaceBuilder
                .setItem(hasFurnace ? Material.FURNACE : Material.RED_STAINED_GLASS_PANE)
                .setName("§5" + configLib.text("interface.furnaceTitle"));

        if (!hasFurnace) {
            furnaceBuilder.setLore("§7" + configLib.text("interface.unlockSlot"));
        }

        menu.setItem(5, furnaceBuilder.create());

        // Creating different spacer elements inbetween

        for (int slot = 0; slot < 9; slot++) {

            if (menu.getItem(slot) != null)
                continue;

            if (slot == 0 || slot == 8) {

                menu.setItem(slot, furnaceBuilder
                        .setItem(Material.BLACK_STAINED_GLASS_PANE)
                        .setName("§7")
                        .create());

                continue;

            }

            menu.setItem(slot, furnaceBuilder
                    .setItem(Material.GRAY_STAINED_GLASS_PANE)
                    .setName("§7")
                    .create());

        }

    }

    /**
     * Handles menu interactions of the mobile crafter gui
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method: 1.1.3
     */
    public void onMenuInteraction(@NotNull final InventoryClickEvent event) {

        final ConfigLib configLib = instance.getConfigLib();
        final CacheService cacheService = instance.getCacheService();
        final Methods methods = instance.getMethods();

        final ItemStack clickedItem = event.getCurrentItem();
        assert clickedItem != null;

        final Inventory clickedInventory = event.getClickedInventory();
        assert clickedInventory != null;

        final Player player = (Player) event.getView().getPlayer();
        final UUID uuid = player.getUniqueId();

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