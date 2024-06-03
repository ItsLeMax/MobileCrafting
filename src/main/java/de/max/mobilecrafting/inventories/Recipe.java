package de.max.mobilecrafting.inventories;

import de.max.mobilecrafting.init.Methods;
import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Recipe {
    public static ItemStack crafter;

    /**
     * Registriert das Craftingrezept
     * <p>
     * Registers the crafting recipe
     *
     * @author ItsLeMax
     */
    public static void register() {
        crafter = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta crafterMeta = crafter.getItemMeta();

        assert crafterMeta != null;
        crafterMeta.setDisplayName("§6" + Methods.language("interface.mobileCrafting"));
        crafterMeta.setLore(Collections.singletonList("§7" + Methods.language("interface.inventoryContent")));
        crafterMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        crafterMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        crafter.setItemMeta(crafterMeta);

        ShapedRecipe crafterRecipe = new ShapedRecipe(
                new NamespacedKey(MobileCrafting.plugin, "crafter"),
                crafter
        );

        crafterRecipe.shape(" F ", " C ", " W ");
        crafterRecipe.setIngredient('F', Material.FURNACE);
        crafterRecipe.setIngredient('C', Material.CHEST);
        crafterRecipe.setIngredient('W', Material.CRAFTING_TABLE);

        Bukkit.addRecipe(crafterRecipe);
    }
}