package de.fpm_studio.mobilecrafting;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.MessageLib;
import de.fpm_studio.ilmlib.util.Template;
import de.fpm_studio.mobilecrafting.commands.GiveMobileCrafter;
import de.fpm_studio.mobilecrafting.events.*;
import de.fpm_studio.mobilecrafting.inventories.MobileCrafterGUI;
import de.fpm_studio.mobilecrafting.inventories.Recipe;
import de.fpm_studio.mobilecrafting.service.CacheService;
import de.fpm_studio.mobilecrafting.util.Methods;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Contains the plugin starting point
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@Getter
public final class MobileCrafting extends JavaPlugin {

    private ConfigLib configLib;
    private MessageLib messageLib;

    private CacheService cacheService;

    private Methods methods;

    private MobileCrafterGUI mobileCrafterGui;
    private Recipe recipe;

    @Override
    public void onEnable() {

        // Initializing custom libs

        configLib = new ConfigLib(this)
                .createDefaultConfigs("config", "storage")
                .createConfigsInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        messageLib = new MessageLib()
                .addSpacing()
                .setPrefix("§cMobileCrafting §7»", true)
                .createTemplateDefaults()
                .setSuffix(new HashMap<>() {{
                    put(Template.SUCCESS, getConfigLib().text("commands.success"));
                    put(Template.ERROR, getConfigLib().text("commands.error"));
                }});

        // Initializing services

        cacheService = new CacheService();

        // Initializing classes

        methods = new Methods(this);

        // Initializing inventories

        mobileCrafterGui = new MobileCrafterGUI(this);

        recipe = new Recipe(this);
        getRecipe().register();

        // Special methods

        registerCommands();
        registerEvents();

        reloadHandling();

        Bukkit.getConsoleSender().sendMessage("§c" + getConfigLib().text("init").replace("%p%", "[MobileCrafting]"));

    }

    /**
     * Registers the plugins commands
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method: 1.1.3
     */
    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("givemobilecrafter").setExecutor(new GiveMobileCrafter(this));
    }

    /**
     * Registers the plugins events
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method: 1.1.3
     */
    private void registerEvents() {

        getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);

    }

    /**
     * Handles reloads and caching the players that are still online
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method: 1.1.3
     */
    private void reloadHandling() {

        for (final Player player : Bukkit.getOnlinePlayers()) {

            if (getCacheService().getMenuCache().get(player.getUniqueId()) != null)
                continue;

            getMethods().createCache(player.getUniqueId());

        }

    }

}