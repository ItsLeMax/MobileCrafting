package de.fpm_studio.mobilecrafting.inventories;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.ItemLib;
import de.fpm_studio.mobilecrafting.service.CacheService;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Contains the gui of the mobile crafter
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class MobileCrafterGUI {

    private final ConfigLib configLib;
    private final CacheService cacheService;

    /**
     * Loads the GUI of the menu
     *
     * @param player Player to load the gui for
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void loadInventory(@NotNull final Player player) {

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

}