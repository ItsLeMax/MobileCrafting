package de.max.mobilecrafting.commands;

import de.max.ilmlib.libraries.MessageLib;
import de.max.ilmlib.utility.HoverText;
import de.max.mobilecrafting.inventories.Recipe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.max.mobilecrafting.init.MobileCrafting.configLib;
import static de.max.mobilecrafting.init.MobileCrafting.messageLib;

public class GiveMobileCrafter implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 0) {
            messageLib.sendInfo(sender, MessageLib.Template.ERROR, configLib.lang("commands.tooManyArgs"), new HoverText("/givemobilecrafter"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            messageLib.sendInfo(sender, MessageLib.Template.ERROR, configLib.lang("commands.playerOnly"));
            return true;
        }

        if (!player.hasPermission("mobilecrafting.give")) {
            messageLib.sendInfo(player, MessageLib.Template.ERROR, configLib.lang("commands.opOnly"));
            return true;
        }

        player.getInventory().addItem(Recipe.crafter);
        messageLib.sendInfo(player, MessageLib.Template.SUCCESS, configLib.lang("commands.granted"));
        return true;
    }
}