package de.max.mobilecrafting.inventories;

import de.max.ilmlib.libraries.ItemLib;
import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;

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
        crafter = new ItemLib()
                .setItem(Material.CRAFTING_TABLE)
                .setName("§c" + configLib.lang("interface.mobileCraftingName"))
                .setLore("§7" + configLib.lang("interface.mobileCraftingLore"))
                .addEnchantment(Enchantment.ARROW_INFINITE, true)
                .create();

        ShapedRecipe crafterRecipe = new ShapedRecipe(new NamespacedKey(MobileCrafting.plugin, "crafter"), crafter);

        crafterRecipe.shape("  W", "  B");
        crafterRecipe.setIngredient('W', Material.CRAFTING_TABLE);
        crafterRecipe.setIngredient('B', Material.BARREL);

        Bukkit.addRecipe(crafterRecipe);
    }
}