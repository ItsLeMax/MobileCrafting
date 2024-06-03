package de.max.mobilecrafting.events;

import de.max.mobilecrafting.init.Config;
import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InventoryClick implements Listener {
    @EventHandler
    public static void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getView().getPlayer();
        UUID uuid = player.getUniqueId();

        ItemStack subMenuItem = event.getCurrentItem();
        Inventory menuInventory = event.getClickedInventory();

        if (subMenuItem == null || menuInventory == null) {
            return;
        }

        if (!menuInventory.equals(MobileCrafting.playerCache.get(uuid).get("MENU"))) {
            return;
        }

        event.setCancelled(true);

        if (subMenuItem.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
            return;
        }

        String subMenuType = subMenuItem.getType().toString();
        if (subMenuType.equals("CRAFTING_TABLE")) subMenuType = "WORKBENCH";
        player.openInventory((Inventory) MobileCrafting.playerCache.get(uuid).get(subMenuType));

        Sound sound = switch (subMenuItem.getType()) {
            case CRAFTING_TABLE -> Sound.BLOCK_CHEST_OPEN;
            case FURNACE -> Sound.ITEM_FIRECHARGE_USE;
            default -> throw new IllegalStateException("Missing SFX for " + subMenuItem.getType());
        };

        player.playSound(player.getLocation(), sound, 1, 1);
        Inventory openedSubMenu = ((Inventory) MobileCrafting.playerCache.get(uuid).get(subMenuType));

        for (int index = 0; index < openedSubMenu.getType().getDefaultSize(); index++) {
            ItemStack craftingItem = (ItemStack) Config.getConfig("storage").get(uuid + "." + subMenuType + "." + index);
            if (craftingItem == null) continue;

            openedSubMenu.setItem(index, craftingItem);
        }
    }
}