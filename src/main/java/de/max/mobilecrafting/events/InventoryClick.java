package de.max.mobilecrafting.events;

import de.max.ilmlib.libraries.ItemLib;
import de.max.mobilecrafting.init.MobileCrafting;
import de.max.mobilecrafting.inventories.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;
import java.util.UUID;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;

public class InventoryClick implements Listener {
    private static final int smeltingTime = 600 / 4; // "/ 4" == Test
    private static final String progressbar = "§7▒▒▒▒▒▒▒▒▒▒ §c0%";

    @EventHandler
    public static void inventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedItem == null || clickedInventory == null) {
            return;
        }

        Player player = (Player) event.getView().getPlayer();
        UUID uuid = player.getUniqueId();

        // Cancellation of unwanted behaviour

        for (Object cacheElement : MobileCrafting.playerCache.get(uuid).values()) {
            if (!(cacheElement instanceof Inventory)) {
                continue;
            }

            if (!clickedInventory.equals(cacheElement)) {
                continue;
            }

            if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
            }
        }

        if (event.getInventory().equals(MobileCrafting.playerCache.get(uuid).get("WORKBENCH")) && event.isShiftClick()) {
            if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                event.setCancelled(true);
                return;
            }
        }

        // Update interfaces if necessary

        if (clickedInventory.equals(MobileCrafting.playerCache.get(uuid).get("WORKBENCH"))) {
            workbenchCraftingLogic();
        }

        if (clickedInventory.equals(MobileCrafting.playerCache.get(uuid).get("FURNACE"))) {
            furnaceSmeltingLogic(player, clickedInventory);
        }

        // Menu interaction handling

        if (!clickedInventory.equals(MobileCrafting.playerCache.get(uuid).get("MENU"))) {
            return;
        }

        event.setCancelled(true);

        if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            ItemStack cursor = event.getCursor();
            if (cursor == null) {
                return;
            }

            if (cursor.getType().equals(Material.FURNACE)) {
                configLib.getConfig("storage").set(uuid + ".Unlocked." + cursor.getType(), true);
                configLib.save("storage");

                cursor.setAmount(cursor.getAmount() - 1);

                player.closeInventory();
                player.openInventory((Inventory) MobileCrafting.playerCache.get(uuid).get("MENU"));
                GUI.loadInventory(player);

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }

            return;
        }

        if (clickedItem.getType().toString().contains("STAINED_GLASS_PANE")) {
            return;
        }

        // Opening the menus on click interaction with their items loaded etc.

        String subMenuType = clickedItem.getType().toString();
        if (subMenuType.equals("CRAFTING_TABLE")) {
            subMenuType = "WORKBENCH";
        }

        Inventory subMenu = ((Inventory) MobileCrafting.playerCache.get(uuid).get(subMenuType));
        player.openInventory(subMenu);

        if (subMenuType.equals("WORKBENCH")) {
            workbenchCraftingLogic();
        }

        if (subMenuType.equals("FURNACE")) {
            furnaceSmeltingLogic(player, subMenu);
        }

        Sound sound = switch (clickedItem.getType()) {
            case CRAFTING_TABLE -> Sound.BLOCK_WOOD_PLACE;
            case FURNACE -> Sound.ITEM_FIRECHARGE_USE;
            default -> throw new RuntimeException();
        };

        player.playSound(player.getLocation(), sound, 1, 1);

        FileConfiguration storage = configLib.getConfig("storage");

        for (int slot = 0; slot < subMenu.getType().getDefaultSize(); slot++) {
            String path = uuid + ".Inventory." + subMenuType + "." + slot;

            if (storage.get(path) == null) {
                continue;
            }

            subMenu.setItem(slot, (ItemStack) storage.get(path));
        }
    }

    private static void workbenchCraftingLogic() {
        // WiP
    }

    private static void furnaceSmeltingLogic(Player player, Inventory subMenu) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        ItemStack fuel = subMenu.getItem(0);
        ItemStack smelt = subMenu.getItem(1);
        ItemStack smelted = subMenu.getItem(2);

        if (smelted == null) {
            subMenu.setItem(2, new ItemLib()
                    .setItem(Material.RED_STAINED_GLASS_PANE)
                    .setName("§c" + configLib.lang("interface.progressName"))
                    .setLore(progressbar)
                    .create());
        }

        UUID uuid = player.getUniqueId();

        if (fuel != null && smelt != null) {
            MobileCrafting.playerCache.get(uuid).put("schedulerDelayed", Bukkit.getScheduler().scheduleSyncDelayedTask(MobileCrafting.plugin, () -> {
                ItemStack result = null;
                Iterator<Recipe> iter = Bukkit.recipeIterator();

                while (iter.hasNext()) {
                    Recipe recipe = iter.next();

                    if (!(recipe instanceof FurnaceRecipe)) {
                        continue;
                    }
                    if (((FurnaceRecipe) recipe).getInput().getType() != fuel.getType()) {
                        continue;
                    }

                    result = recipe.getResult();
                    break;
                }

                if (result == null) {
                    return;
                }

                result.setAmount(fuel.getAmount());
            }, smeltingTime));

            // Smelting process

            MobileCrafting.playerCache.get(uuid).put("schedulerProgress", 0);
            MobileCrafting.playerCache.get(uuid).put("schedulerRepeat", Bukkit.getScheduler().scheduleSyncRepeatingTask(MobileCrafting.plugin, () -> {
                assert smelted != null;
                if (!smelted.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                    return;
                }

                MobileCrafting.playerCache.get(uuid).put("schedulerProgress", (int) MobileCrafting.playerCache.get(uuid).get("schedulerProgress") + 10);

                new ItemLib()
                        .setItem(smelted)
                        .setLore(progressbar.replaceFirst("▒", "█").replaceFirst(progressbar.substring(progressbar.length() - 3).replaceFirst(" ", ""), MobileCrafting.playerCache.get(uuid).get("schedulerProgress") + "%"))
                        .create();
            }, 0, smeltingTime / 10));

            return;
        }

        // Reset on end (WiP)

        if (MobileCrafting.playerCache.get(uuid).get("schedulerDelayed") != null) {
            Bukkit.getScheduler().cancelTask((int) MobileCrafting.playerCache.get(uuid).get("schedulerDelayed"));
        }

        if (MobileCrafting.playerCache.get(uuid).get("schedulerRepeat") != null) {
            Bukkit.getScheduler().cancelTask((int) MobileCrafting.playerCache.get(uuid).get("schedulerRepeat"));
        }
    }
}