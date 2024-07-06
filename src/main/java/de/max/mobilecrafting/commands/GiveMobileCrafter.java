package de.max.mobilecrafting.commands;

import de.max.mobilecrafting.init.Methods;
import de.max.mobilecrafting.inventories.Recipe;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;

public class GiveMobileCrafter implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 0) {
            Methods.info(sender, 'c', configLib.lang("commands.tooManyArgs"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            Methods.info(sender, 'c', configLib.lang("commands.playerOnly"));
            return true;
        }

        if (!player.hasPermission("mobilecrafting.give")) {
            Methods.info(sender, 'c', configLib.lang("commands.opOnly"));
            return true;
        }

        player.getInventory().addItem(Recipe.crafter);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        return true;
    }
}
