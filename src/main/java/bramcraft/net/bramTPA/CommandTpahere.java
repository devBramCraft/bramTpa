package bramcraft.net.bramTPA;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandTpahere implements CommandExecutor {

    public static final HashMap<UUID, UUID> hereRequests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player requester = (Player) sender;
        FileConfiguration config = BramTPA.getInstance().getConfig();

        if (!requester.hasPermission("bramtpa.tpahere")) {
            requester.sendMessage(color(config.getString("messages.no-permission")));
            return true;
        }

        if (args.length != 1) {
            requester.sendMessage(color("&cUsage: /tpahere <player>"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline() || target == requester) {
            requester.sendMessage(color(config.getString("messages.player-not-found")));
            return true;
        }

        // Save the request
        hereRequests.put(target.getUniqueId(), requester.getUniqueId());

        // Message with [ACCEPT] and [DENY]
        String baseMsg = config.getString("messages.here-request-received", "&e%player% wants you to teleport to them.");
        TextComponent msg = new TextComponent(color(baseMsg.replace("%player%", requester.getName()) + " "));
        TextComponent accept = new TextComponent(ChatColor.GREEN + "[ACCEPT]");
        TextComponent deny = new TextComponent(ChatColor.RED + " [DENY]");

        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));

        msg.addExtra(accept);
        msg.addExtra(deny);

        target.spigot().sendMessage(msg);

        // Sound
        playSound(target, config.getString("sounds.request"));

        // Confirm to sender
        requester.sendMessage(color(config.getString("messages.request-sent").replace("%target%", target.getName())));
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
