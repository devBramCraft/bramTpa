package bramcraft.net.bramTPA;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatMessageType;

public class CommandTpaccept implements CommandExecutor {

    public static HashSet<UUID> teleportingPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player target = (Player) sender;
        FileConfiguration config = BramTPA.getInstance().getConfig();

        if (!target.hasPermission("bramtpa.tpaccept")) {
            target.sendMessage(color(config.getString("messages.no-permission")));
            return true;
        }

        // Check of er een TPA of TPAHERE request is
        UUID requesterUUID = CommandTpa.requests.get(target.getUniqueId());
        boolean isHereRequest = false;

        if (requesterUUID == null) {
            requesterUUID = CommandTpahere.hereRequests.get(target.getUniqueId());
            isHereRequest = true;
        }

        if (requesterUUID == null) {
            target.sendMessage(color(config.getString("messages.no-request")));
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUUID);
        if (requester == null || !requester.isOnline()) {
            target.sendMessage(color(config.getString("messages.no-request")));
            CommandTpa.requests.remove(target.getUniqueId());
            CommandTpahere.hereRequests.remove(target.getUniqueId());
            return true;
        }

        target.sendMessage(color(config.getString("messages.accepted")));
        requester.sendMessage(color(config.getString("messages.accepted-sender")));

        playSound(requester, config.getString("sounds.accept"));

        int delay = config.getInt("teleport-delay", 5);
        UUID teleportingUUID = isHereRequest ? target.getUniqueId() : requester.getUniqueId();
        teleportingPlayers.add(teleportingUUID);

        boolean finalIsHereRequest = isHereRequest;
        new BukkitRunnable() {
            int seconds = delay;

            @Override
            public void run() {
                if (!teleportingPlayers.contains(teleportingUUID)) {
                    cancel();
                    return;
                }

                if (seconds > 0) {
                    String msg = config.getString("messages.teleporting").replace("%seconds%", seconds + "");
                    Player toShow = finalIsHereRequest ? target : requester;
                    toShow.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            new TextComponent(color(msg)));
                    seconds--;
                } else {
                    if (finalIsHereRequest) {
                        target.teleport(requester.getLocation());
                    } else {
                        requester.teleport(target.getLocation());
                    }

                    Player toShow = finalIsHereRequest ? target : requester;
                    toShow.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            new TextComponent(color(config.getString("messages.teleported"))));

                    teleportingPlayers.remove(teleportingUUID);
                    cancel();
                }
            }
        }.runTaskTimer(BramTPA.getInstance(), 0L, 20L);

        // Verwijder het verzoek uit beide mappen
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
