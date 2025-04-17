package bramcraft.net.bramTPA;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TPAToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry Console but only players can use this command");
            return true;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (BramTPA.tpaDisabled.contains(uuid)) {
            BramTPA.tpaDisabled.remove(uuid);
            player.sendMessage(ChatColor.GREEN + "Your TPA has been Enabled");
        } else {
            BramTPA.tpaDisabled.add(uuid);
            player.sendMessage(ChatColor.RED + "Your TPA has been Disabled");
        }

        return true;
    }
}
