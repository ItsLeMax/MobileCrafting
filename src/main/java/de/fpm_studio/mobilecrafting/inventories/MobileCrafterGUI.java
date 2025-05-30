package de.fpm_studio.mobilecrafting.inventories;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.ItemLib;
import de.fpm_studio.mobilecrafting.data.CustomInventoryType;
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

    /**
     * Loads the GUI of the menu
     *
     * @param player Player to load the gui for
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void loadInventory(@NotNull final Player player) {

        final ItemLib itemBuilder = new ItemLib();

        // Loading the player bound mobile crafter main menu...

        final Inventory inventory = (Inventory) CacheService.playerCache
                .get(player.getUniqueId()).get(CustomInventoryType.MENU);

        player.openInventory(inventory);

        // ...with its items

        inventory.setItem(3, itemBuilder
                .setItem(Material.CRAFTING_TABLE)
                .setName("§c" + configLib.text("interface.workbenchTitle"))
                .create());

        // ...depending if they each are unlocked

        final boolean hasFurnace = configLib.getConfig("storage")
                .getBoolean(player.getUniqueId() + ".Unlocked.FURNACE");

        itemBuilder
                .setItem(hasFurnace ? Material.FURNACE : Material.RED_STAINED_GLASS_PANE)
                .setName("§5" + configLib.text("interface.furnaceTitle"));

        if (!hasFurnace) {
            itemBuilder.setLore("§7" + configLib.text("interface.unlockSlot"));
        }

        inventory.setItem(5, itemBuilder.create());

        // Creating different spacer elements inbetween

        for (int slot = 0; slot < 9; slot++) {

            if (inventory.getItem(slot) != null)
                continue;

            if (slot == 0 || slot == 8) {

                inventory.setItem(slot, itemBuilder
                        .setItem(Material.BLACK_STAINED_GLASS_PANE)
                        .setName("§7")
                        .create());

                continue;

            }

            inventory.setItem(slot, itemBuilder
                    .setItem(Material.GRAY_STAINED_GLASS_PANE)
                    .setName("§7")
                    .create());

        }

    }

}