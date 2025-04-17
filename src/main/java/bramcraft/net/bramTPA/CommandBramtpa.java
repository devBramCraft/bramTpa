package bramcraft.net.bramTPA;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandBramtpa implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "----------[ bramTPA Help ]----------");
            sender.sendMessage(ChatColor.GREEN + "/tpa <player> " + ChatColor.GRAY + "- Send teleport request");
            sender.sendMessage(ChatColor.GREEN + "/tpahere <player> " + ChatColor.GRAY + "- Ask to teleport to you");
            sender.sendMessage(ChatColor.GREEN + "/tpaccept " + ChatColor.GRAY + "- Accept request");
            sender.sendMessage(ChatColor.GREEN + "/tpdeny " + ChatColor.GRAY + "- Deny request");
            sender.sendMessage(ChatColor.GREEN + "/bramtpa reload " + ChatColor.GRAY + "- Reload config");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("bramtpa.admin")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            BramTPA.getInstance().reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "bramTPA config reloaded!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /bramtpa.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload");
        }
        return null;
    }
}