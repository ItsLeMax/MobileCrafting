package de.max.mobilecrafting.init;

import de.max.ilmlib.init.ILMLib;
import de.max.ilmlib.libraries.ConfigLib;
import de.max.mobilecrafting.commands.GiveMobileCrafter;
import de.max.mobilecrafting.events.*;
import de.max.mobilecrafting.inventories.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class MobileCrafting extends JavaPlugin {
    public static MobileCrafting plugin;
    public static HashMap<UUID, HashMap<String, Object>> playerCache = new HashMap<>();
    public static ConfigLib configLib;

    @Override
    public void onEnable() {
        plugin = this;

        configLib = new ILMLib(plugin).getConfigLib();
        configLib
                .createDefaults("config", "storage")
                .createInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        Recipe.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerCache.get(player.getUniqueId()) != null) continue;
            Methods.createCache(player.getUniqueId());
        }

        getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);
        getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
        getServer().getPluginManager().registerEvents(new InventoryClose(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), plugin);

        Objects.requireNonNull(getCommand("givemobilecrafter")).setExecutor(new GiveMobileCrafter());

        Bukkit.getConsoleSender().sendMessage("§c" + configLib.lang("general.init"));
    }

    /**
     * Sendet den Cache in Intervallen in die Konsole
     * <p>
     * Sends the cache in intervals into the console
     *
     * @author ItsLeMax
     */
    @SuppressWarnings("unused")
    private void log() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getConsoleSender().sendMessage("§8" + playerCache), 0, 200);
    }
}