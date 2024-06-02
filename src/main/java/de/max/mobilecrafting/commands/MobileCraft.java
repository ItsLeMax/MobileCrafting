package de.max.mobilecrafting.commands;

import de.max.mobilecrafting.inventories.Recipe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MobileCraft implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (!player.isOp()) {
            return true;
        }

        player.getInventory().addItem(Recipe.crafter);
        return true;
    }
}
