package de.fpm_studio.mobilecrafting.inventories;

import de.max.ilmlib.libraries.ItemLib;
import de.fpm_studio.mobilecrafting.MobileCrafting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static de.fpm_studio.mobilecrafting.MobileCrafting.configLib;

public class GUI {
    /**
     * Lädt das GUI des Menüs
     * <p>
     * Loads the GUI of the menu
     *
     * @author ItsLeMax
     */
    public static void loadInventory(Player player) {
        ItemLib itemLib = new ItemLib();

        Inventory inventory = (Inventory) MobileCrafting.playerCache.get(player.getUniqueId()).get("MENU");
        player.openInventory(inventory);

        inventory.setItem(3, itemLib
                .setItem(Material.CRAFTING_TABLE)
                .setName("§c" + configLib.lang("interface.workbenchTitle"))
                .create());

        boolean hasFurnace = configLib.getConfig("storage").getBoolean(player.getUniqueId() + ".Unlocked.FURNACE");

        itemLib
                .setItem(hasFurnace ? Material.FURNACE : Material.RED_STAINED_GLASS_PANE)
                .setName("§5" + configLib.lang("interface.furnaceTitle"));

        if (!hasFurnace) {
            itemLib.setLore("§7" + configLib.lang("interface.unlockSlot"));
        }

        inventory.setItem(5, itemLib.create());

        for (int slot = 0; slot < 9; slot++) {
            if (inventory.getItem(slot) != null) {
                continue;
            }

            if (slot == 0 || slot == 8) {
                inventory.setItem(slot, itemLib
                        .setItem(Material.BLACK_STAINED_GLASS_PANE)
                        .setName("§7")
                        .create());
                continue;
            }

            inventory.setItem(slot, itemLib
                    .setItem(Material.GRAY_STAINED_GLASS_PANE)
                    .setName("§7")
                    .create());
        }
    }
}