package de.fpm_studio.mobilecrafting.registry;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.ItemLib;
import de.fpm_studio.mobilecrafting.MobileCrafting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Contains the vanilla crafting recipe for the plugins item
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@RequiredArgsConstructor
public final class ItemRegistry {

    private final MobileCrafting instance;

    @Getter
    public ItemStack crafter;

    /**
     * Registers the crafting recipe
     *
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void register() {

        final ConfigLib configLib = instance.getConfigLib();

        crafter = new ItemLib()
                .setItem(Material.CRAFTING_TABLE)
                .setName("§c" + configLib.text("interface.mobileCraftingName"))
                .setLore("§7" + configLib.text("interface.mobileCraftingLore"))
                .addEnchantment(Enchantment.ARROW_INFINITE, true)
                .create();

        // Shape of the recipe item stacks

        final ShapedRecipe crafterRecipe = new ShapedRecipe(new NamespacedKey(instance, "crafter"), crafter);

        crafterRecipe.shape("  W", "  B");
        crafterRecipe.setIngredient('W', Material.CRAFTING_TABLE);
        crafterRecipe.setIngredient('B', Material.BARREL);

        Bukkit.addRecipe(crafterRecipe);

    }

}