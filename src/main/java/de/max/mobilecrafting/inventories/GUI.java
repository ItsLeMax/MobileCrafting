package de.max.mobilecrafting.inventories;

import de.max.mobilecrafting.init.Methods;
import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class GUI {
    /**
     * Lädt das GUI des Craftingmenüs
     * <p>
     * Loads the GUI of the crafting menu
     *
     * @author ItsLeMax
     */
    public static void loadInventory(Player player) {
        Inventory inventory = (Inventory) MobileCrafting.playerCache.get(player.getUniqueId()).get("MENU");
        player.openInventory(inventory);

        ItemStack crafting = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftingMeta = crafting.getItemMeta();
        assert craftingMeta != null;
        craftingMeta.setDisplayName("§c" + Methods.language("interface.mobileCraftingTitle"));
        craftingMeta.setLore(Collections.singletonList("§7" + Methods.language("interface.mobileCraftingLore")));
        crafting.setItemMeta(craftingMeta);

        inventory.setItem(3, crafting);

        ItemStack smelting = new ItemStack(Material.FURNACE);
        ItemMeta smeltingMeta = smelting.getItemMeta();
        assert smeltingMeta != null;
        smeltingMeta.setDisplayName("§5" + Methods.language("interface.mobileSmeltingTitle"));
        smeltingMeta.setLore(Collections.singletonList("§7" + Methods.language("interface.mobileSmeltingLore")));
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