package de.max.mobilecrafting.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

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
        Inventory inventory = (Inventory) playerCache.get(player.getUniqueId()).get("MENU");
        player.openInventory(inventory);

        boolean hasFurnace = configLib.getConfig("storage").getBoolean(player.getUniqueId() + ".Unlocked.FURNACE");

        ItemStack crafting = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftingMeta = crafting.getItemMeta();
        assert craftingMeta != null;
        craftingMeta.setDisplayName("§c" + configLib.lang("interface.workbenchTitle"));
        crafting.setItemMeta(craftingMeta);

        inventory.setItem(3, crafting);

        ItemStack smelting = new ItemStack(hasFurnace ? Material.FURNACE : Material.RED_STAINED_GLASS_PANE);
        ItemMeta smeltingMeta = smelting.getItemMeta();
        assert smeltingMeta != null;
        smeltingMeta.setDisplayName("§5" + configLib.lang("interface.furnaceTitle"));
        if (!hasFurnace) {
            smeltingMeta.setLore(Collections.singletonList("§7" + configLib.lang("interface.unlockSlot")));
        }
        smelting.setItemMeta(smeltingMeta);

        inventory.setItem(5, smelting);

        for (int index = 0; index < 9; index++) {
            if (inventory.getItem(index) != null) continue;

            ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta placeholderMeta = placeholder.getItemMeta();
            assert placeholderMeta != null;
            placeholderMeta.setDisplayName("§7");
            placeholder.setItemMeta(placeholderMeta);

            inventory.setItem(index, placeholder);
        }
    }
}