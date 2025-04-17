package bramcraft.net.bramTPA;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandTpa implements CommandExecutor {

    public static final HashMap<UUID, UUID> requests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player requester = (Player) sender;

        if (!requester.hasPermission("bramtpa.tpa")) {
            requester.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            requester.sendMessage(ChatColor.RED + "Usage: /tpa <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline() || target == requester) {
            requester.sendMessage(ChatColor.RED + "Invalid player.");
            return true;
        }

        // Save request
        requests.put(target.getUniqueId(), requester.getUniqueId());

        // Klikbaar bericht
        TextComponent message = new TextComponent(ChatColor.YELLOW + requester.getName() + " wants to teleport to you. ");
        TextComponent accept = new TextComponent(ChatColor.GREEN + "[ACCEPT]");
        TextComponent deny = new TextComponent(ChatColor.RED + " [DENY]");

        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));

        message.addExtra(accept);
        message.addExtra(deny);

        target.spigot().sendMessage(message);
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

        requester.sendMessage(ChatColor.GREEN + "Teleport request sent to " + target.getName() + ".");
        return true;
    }
}
