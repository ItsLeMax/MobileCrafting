package de.fpm_studio.mobilecrafting;

import de.fpm_studio.mobilecrafting.events.*;
import de.fpm_studio.mobilecrafting.util.Methods;
import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.MessageLib;
import de.fpm_studio.mobilecrafting.commands.GiveMobileCrafter;
import de.max.mobilecrafting.events.*;
import de.fpm_studio.mobilecrafting.inventories.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class MobileCrafting extends JavaPlugin {
    public static MobileCrafting plugin;
    public static HashMap<UUID, HashMap<String, Object>> playerCache = new HashMap<>();

    public static ConfigLib configLib;
    public static MessageLib messageLib;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {
        plugin = this;

        configLib = new ConfigLib(this)
                .createDefaults("config", "storage")
                .createInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        messageLib = new MessageLib()
                .addSpacing()
                .setPrefix("§cMobileCrafting §7»", true)
                .createDefaults()
                .setSuffix(new HashMap<>() {{
                    put(MessageLib.Template.SUCCESS, configLib.lang("commands.success"));
                    put(MessageLib.Template.ERROR, configLib.lang("commands.error"));
                }});

        Recipe.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerCache.get(player.getUniqueId()) != null) {
                continue;
            }

            Methods.createCache(player.getUniqueId());
        }

        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        getCommand("givemobilecrafter").setExecutor(new GiveMobileCrafter());

        Bukkit.getConsoleSender().sendMessage("§c" + configLib.lang("init").replace("%p%", "[MobileCrafting]"));
    }
}