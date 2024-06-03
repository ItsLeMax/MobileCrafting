package de.max.mobilecrafting.init;

import de.max.mobilecrafting.commands.MobileCraft;
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

    @Override
    public void onEnable() {
        plugin = this;

        Config.createConfigs(plugin);
        saveDefaultConfig();

        Recipe.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerCache.get(player.getUniqueId()) != null) continue;
            Methods.createCache(player.getUniqueId());
        }

        // TODO:
        // Items in Ofen und Werkbank adäquat speichern und laden, falls Restart und Items immernoch darin

        getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);
        getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), plugin);

        Objects.requireNonNull(getCommand("mobilecraft")).setExecutor(new MobileCraft());

        Bukkit.getConsoleSender().sendMessage("§6" + Methods.language("general.init"));
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