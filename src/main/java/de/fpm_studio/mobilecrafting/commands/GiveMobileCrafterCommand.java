package de.fpm_studio.mobilecrafting.commands;

import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.MessageLib;
import de.fpm_studio.ilmlib.util.HoverText;
import de.fpm_studio.ilmlib.util.Template;
import de.fpm_studio.mobilecrafting.MobileCrafting;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Contains the {@code /givemobilecrafter} command to cheat said item
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class GiveMobileCrafterCommand implements CommandExecutor {

    private final MobileCrafting instance;

    @Override
    public boolean onCommand(
            @NotNull final CommandSender sender,
            @NotNull final Command command,
            @NotNull final String label,
            final String[] args) {

        final ConfigLib configLib = instance.getConfigLib();
        final MessageLib messageLib = instance.getMessageLib();

        if (!sender.hasPermission("mobilecrafting.give")) {
            messageLib.sendInfo(sender, Template.ERROR, configLib.text("commands.opOnly"));
            return true;
        }

        if (!(sender instanceof final Player player)) {
            messageLib.sendInfo(sender, Template.ERROR, configLib.text("commands.playerOnly"));
            return true;
        }

        if (args.length > 0) {

            final HoverText hoverText = new HoverText("/givemobilecrafter");
            messageLib.sendInfo(player, Template.ERROR, configLib.text("commands.tooManyArgs"), hoverText);

            return true;

        }

        // Cheat the mobile crafter item with a chat info

        player.getInventory().addItem(instance.getItemRegistry().getCrafter());
        messageLib.sendInfo(player, Template.SUCCESS, configLib.text("commands.granted"));

        return true;

    }

}