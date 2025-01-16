package de.max.mobilecrafting.init;

import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.MessageLib;
import de.max.ilmlib.utility.ErrorTemplate;
import de.max.ilmlib.utility.SuccessTemplate;
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
    public static MessageLib messageLib;

    @Override
    public void onEnable() {
        plugin = this;

        configLib = new ConfigLib()
                .setPlugin(this)
                .createDefaults("config", "storage")
                .createInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        messageLib = new MessageLib()
                .addSpacing()
                .createDefaults()
                .setPrefix("§cMobileCrafting §7»", true);

        new SuccessTemplate().setSuffix(configLib.lang("commands.success"));
        new ErrorTemplate().setSuffix(configLib.lang("commands.error"));

        Recipe.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerCache.get(player.getUniqueId()) != null) continue;
            Methods.createCache(player.getUniqueId());
        }

        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        Objects.requireNonNull(getCommand("givemobilecrafter")).setExecutor(new GiveMobileCrafter());

        Bukkit.getConsoleSender().sendMessage("§c" + configLib.lang("init").replace("%p%", "[MobileCrafting]"));
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