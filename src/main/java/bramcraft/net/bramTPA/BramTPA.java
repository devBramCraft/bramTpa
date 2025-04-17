package bramcraft.net.bramTPA;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BramTPA extends JavaPlugin {
    public static Set<UUID> tpaDisabled = new HashSet<>();

    private static BramTPA instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("tpatoggle").setExecutor(new TPAToggleCommand());
        getCommand("tpa").setExecutor(new CommandTpa());
        getCommand("tpaccept").setExecutor(new CommandTpaccept());
        getCommand("tpdeny").setExecutor(new CommandTpdeny());
        getCommand("tpahere").setExecutor(new CommandTpahere());
        Bukkit.getPluginManager().registerEvents(new TpaListener(), this);
        CommandBramtpa bramCommand = new CommandBramtpa();
        getCommand("bramtpa").setExecutor(bramCommand);
        getCommand("bramtpa").setTabCompleter(bramCommand);

        getLogger().info("bramTPA plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("bramTPA plugin has been disabled!");
    }

    public static BramTPA getInstance() {
        return instance;
    }
}
