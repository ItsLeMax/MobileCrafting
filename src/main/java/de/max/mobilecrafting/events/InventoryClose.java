package de.max.mobilecrafting.events;

import de.max.mobilecrafting.init.Config;
import de.max.mobilecrafting.init.MobileCrafting;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class InventoryClose implements Listener {
    @EventHandler
    public static void inventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        FileConfiguration config = Config.getConfig("storage");

        if (clickedInventoryEquals(event, "FURNACE", "WORKBENCH")) {
            for (int index = 0; index < inventory.getType().getDefaultSize(); index++) {
                config.set(event.getView().getPlayer().getUniqueId() + "." + inventory.getType() + "." + index, inventory.getItem(index));
            }

            try {
                config.save(Config.getFile("storage"));
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
    private static boolean clickedInventoryEquals(InventoryCloseEvent event, String... GUIs) {
        UUID uuid = event.getPlayer().getUniqueId();

        for (String GUI : GUIs) {
            if (Objects.equals(event.getInventory(), MobileCrafting.playerCache.get(uuid).get(GUI))) {
                return true;
            }
        }

        return false;
    }
}