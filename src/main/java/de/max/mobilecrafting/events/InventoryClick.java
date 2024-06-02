package de.max.mobilecrafting.events;

import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;
import java.util.UUID;

public class InventoryClick implements Listener {
    @EventHandler
    public static void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getView().getPlayer();
        UUID uuid = player.getUniqueId();
        if (!Objects.equals(event.getClickedInventory(), MobileCrafting.playerCache.get(uuid).get("Menu"))) {
            return;
        }

        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        Material type = event.getCurrentItem().getType();

        if (type.equals(Material.CRAFTING_TABLE)) {
            player.openInventory((Inventory) MobileCrafting.playerCache.get(uuid).get("Workbench"));
        }

        if (type.equals(Material.FURNACE)) {
            player.openInventory((Inventory) MobileCrafting.playerCache.get(uuid).get("Furnace"));
        }
    }
}
