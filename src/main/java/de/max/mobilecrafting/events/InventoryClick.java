package de.max.mobilecrafting.events;

import de.max.mobilecrafting.inventories.GUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;
import static de.max.mobilecrafting.init.MobileCrafting.playerCache;

public class InventoryClick implements Listener {
    @EventHandler
    public static void inventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedItem == null || clickedInventory == null) {
            return;
        }

        Player player = (Player) event.getView().getPlayer();
        UUID uuid = player.getUniqueId();

        if (event.getInventory().equals(playerCache.get(uuid).get("WORKBENCH")) && event.isShiftClick()) {
            if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                event.setCancelled(true);
                return;
            }
        }

        if (!clickedInventory.equals(playerCache.get(uuid).get("MENU"))) {
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
                player.openInventory((Inventory) playerCache.get(uuid).get("MENU"));
                GUI.loadInventory(player);

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }

            return;
        }

        if (clickedItem.getType().toString().contains("STAINED_GLASS_PANE")) {
            return;
        }

        String subMenuType = clickedItem.getType().toString();
        if (subMenuType.equals("CRAFTING_TABLE")) subMenuType = "WORKBENCH";

        Inventory subMenu = ((Inventory) playerCache.get(uuid).get(subMenuType));
        player.openInventory(subMenu);

        Sound sound = switch (clickedItem.getType()) {
            case CRAFTING_TABLE -> Sound.BLOCK_WOOD_PLACE;
            case FURNACE -> Sound.ITEM_FIRECHARGE_USE;
            default -> throw new RuntimeException();
        };

        player.playSound(player.getLocation(), sound, 1, 1);

        for (int slot = 0; slot < subMenu.getType().getDefaultSize(); slot++) {
            ItemStack craftingItem = (ItemStack) configLib.getConfig("storage").get(uuid + ".Inventory." + subMenuType + "." + slot);
            if (craftingItem == null) {
                continue;
            }

            subMenu.setItem(slot, craftingItem);
        }
    }
}