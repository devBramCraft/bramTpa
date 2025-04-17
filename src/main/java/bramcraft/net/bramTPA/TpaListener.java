package bramcraft.net.bramTPA;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class TpaListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if (CommandTpaccept.teleportingPlayers.contains(uuid)) {
            if (e.getFrom().getX() != e.getTo().getX()
                    || e.getFrom().getY() != e.getTo().getY()
                    || e.getFrom().getZ() != e.getTo().getZ()) {

                CommandTpaccept.teleportingPlayers.remove(uuid);
                FileConfiguration config = BramTPA.getInstance().getConfig();

                String msg = config.getString("messages.cancelled");
                if (msg != null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }

                String soundName = config.getString("sounds.cancel");
                if (soundName != null) {
                    try {
                        Sound s = Sound.valueOf(soundName);
                        p.playSound(p.getLocation(), s, 1, 1);
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        }
    }
}
