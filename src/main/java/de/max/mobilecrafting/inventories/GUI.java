package de.max.mobilecrafting.inventories;

import de.max.ilmlib.libraries.ItemLib;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;
import static de.max.mobilecrafting.init.MobileCrafting.playerCache;

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

        Inventory inventory = (Inventory) playerCache.get(player.getUniqueId()).get("MENU");
        player.openInventory(inventory);

        boolean hasFurnace = configLib.getConfig("storage").getBoolean(player.getUniqueId() + ".Unlocked.FURNACE");

        inventory.setItem(3, itemLib
                .setItem(Material.CRAFTING_TABLE)
                .setName("§c" + configLib.lang("interface.workbenchTitle"))
                .create());

        itemLib
                .setItem(hasFurnace ? Material.FURNACE : Material.RED_STAINED_GLASS_PANE)
                .setName("§5" + configLib.lang("interface.furnaceTitle"));

        if (!hasFurnace) {
            itemLib.setLore("§7" + configLib.lang("interface.unlockSlot"));
        }

        inventory.setItem(5, itemLib.create());

        for (int index = 0; index < 9; index++) {
            if (inventory.getItem(index) != null) continue;

            inventory.setItem(index, new ItemLib()
                    .setItem(Material.GRAY_STAINED_GLASS_PANE)
                    .setName("§7")
                    .create());
        }
    }
}