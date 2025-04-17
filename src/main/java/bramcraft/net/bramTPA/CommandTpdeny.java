package bramcraft.net.bramTPA;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandTpdeny implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player target = (Player) sender;
        FileConfiguration config = BramTPA.getInstance().getConfig();

        if (!target.hasPermission("bramtpa.tpdeny")) {
            target.sendMessage(color(config.getString("messages.no-permission")));
            return true;
        }

        UUID senderUUID = CommandTpa.requests.get(target.getUniqueId());
        if (senderUUID == null) {
            senderUUID = CommandTpahere.hereRequests.get(target.getUniqueId());
        }

        if (senderUUID == null) {
            target.sendMessage(color(config.getString("messages.no-request")));
            return true;
        }

        Player requester = target.getServer().getPlayer(senderUUID);
        if (requester != null) {
            requester.sendMessage(color(config.getString("messages.denied-sender")));
            playSound(requester, config.getString("sounds.deny"));
        }

        target.sendMessage(color(config.getString("messages.denied")));
        CommandTpa.requests.remove(target.getUniqueId());
        CommandTpahere.hereRequests.remove(target.getUniqueId());
        return true;
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private void playSound(Player p, String soundName) {
        if (soundName == null) return;
        try {
            Sound s = Sound.valueOf(soundName);
            p.playSound(p.getLocation(), s, 1, 1);
        } catch (IllegalArgumentException ignored) {}
    }
}
