package de.max.mobilecrafting.events;

import de.max.mobilecrafting.init.Config;
import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class InventoryClick implements Listener {
    @EventHandler
    public static void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getView().getPlayer();
        UUID uuid = player.getUniqueId();

        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getClickedInventory();

        if (clickedInventory(event, "Menu")) {
            event.setCancelled(true);

            if (item == null || inventory == null || item.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                return;
            }

            String invType = item.getType().toString().toLowerCase();
            if (invType.equals("crafting_table")) invType = "Workbench";
            player.openInventory((Inventory) MobileCrafting.playerCache.get(uuid).get(invType.substring(0, 1).toUpperCase() + invType.substring(1)));

            Sound sound = switch (item.getType()) {
                case CRAFTING_TABLE -> Sound.BLOCK_CHEST_OPEN;
                case FURNACE -> Sound.ITEM_FIRECHARGE_USE;
                default -> Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
            };

            player.playSound(player.getLocation(), sound, 1, 1);
        }

        if (clickedInventory(event, "Furnace", "Workbench")) {
            assert inventory != null;

            FileConfiguration config = Config.getConfig("storage");
            String path = uuid + "." + inventory.getType();

            for (int index = 0; index < inventory.getType().getDefaultSize(); index++) {
                item = inventory.getItem(index);
                if (item == null || item.getType().equals(Material.AIR)) continue;

                config.set(path + "." + index, item);
            }

            config.set(path + "." + event.getSlot(), event.getCursor());

            try {
                Config.getConfig("storage").save(Config.getFile("storage"));
            } catch (IOException error) {
                throw new RuntimeException(error);
            }
        }
    }

    /**
     * Überprüft, ob es sich um ein individuelles Inventar handelt, in dem geklickt wurde
     * <p>
     * Checks wheter an inventory is individual in which has been clicked
     *
     * @param GUIs GUIs, in welchen geklickt wurde
     *             <p>
     *             GUIs in which has been clicked
     * @return Boolean, ob individuelles Inventar
     * @author ItsLeMax
     */
    private static boolean clickedInventory(InventoryClickEvent event, String... GUIs) {
        UUID uuid = event.getWhoClicked().getUniqueId();

        for (String gui : GUIs) {
            if (Objects.equals(event.getClickedInventory(), MobileCrafting.playerCache.get(uuid).get(gui))) {
                return true;
            }
        }

        return false;
    }
}
