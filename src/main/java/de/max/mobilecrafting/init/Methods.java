package de.max.mobilecrafting.init;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.UUID;

public class Methods {
    /**
     * Lädt Text je nach gewählter Sprache in der Config
     * <p>
     * Loads text depending on the chosen languages inside the config
     *
     * @param path Pfad zum Text in den Sprachconfigs
     *             <p>
     *             Path to the text in the languages configs
     * @return String mit Text in der gewählten Sprache <p> String with text in the chosen languages
     * @author ItsLeMax (StorageTerminal)
     */
    public static String language(String path) {
        FileConfiguration config = Config.getConfig(MobileCrafting.plugin.getConfig().getString("language"));

        if (config == null) {
            info(Bukkit.getConsoleSender(), 'e', "Language inside config.yml does not exist inside StorageTerminal\\languages. English has been chosen instead.");
            config = Config.getConfig("en_US");
        }

        if (config.getString(path) == null) {
            return "§c§lError in languages file";
        }

        return config.getString(path);
    }

    /**
     * Sendet Informationen an den Benutzer
     * <p>
     * Sends information to the user
     *
     * @param colorCode Farbcode, welche verwendet wird
     *                  <p>
     *                  Color code, which will be used
     * @param message   Nachricht, welche an den Benutzer gesendet wird
     *                  <p>
     *                  Message, that will be sent to the user
     * @author ItsLeMax
     */
    public static void info(CommandSender sender, char colorCode, String message) {
        sender.sendMessage("");
        sender.sendMessage("§8> §6MobileCrafting");
        sender.sendMessage("§" + colorCode + message);
        sender.sendMessage("");
    }

    /**
     * Erstellt den Inventarcache
     * <p>
     * Creates the inventory cache
     *
     * @author ItsLeMax
     */
    public static void createCache(UUID uuid) {
        MobileCrafting.playerCache.put(uuid, new HashMap<>());
        MobileCrafting.playerCache.get(uuid).put("MENU", Bukkit.createInventory(null, 9, "§c" + Methods.language("interface.mobileCrafting")));
        MobileCrafting.playerCache.get(uuid).put("WORKBENCH", Bukkit.createInventory(null, InventoryType.WORKBENCH, "§c" + Methods.language("interface.mobileCraftingTitle")));
        MobileCrafting.playerCache.get(uuid).put("FURNACE", Bukkit.createInventory(null, InventoryType.FURNACE, "§5" + Methods.language("interface.mobileSmeltingTitle")));
    }
}
