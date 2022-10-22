package pl.elmah.portalblocker.core;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import pl.elmah.portalblocker.event.BukkitEventListener;
import pl.elmah.portalblocker.event.EventHandler;

public class PortalBlocker extends JavaPlugin {
    private static PortalBlocker Instance;
    private PluginConfiguration PluginConfig;
    private CommandHandler DefaultCommandHandler;

    @Override
    public void onEnable() {
        Instance = this;
        DefaultCommandHandler = new CommandHandler();

        RegisterEventHandlers();
        RegisterCommands();
        LoadConfiguration();

        PluginDescriptionFile desc = getDescription();
        String initInfo = String.format("%s version %s enabled",
                desc.getName(),
                desc.getVersion());
        getLogger().info(initInfo);
    }

    public PluginConfiguration getPluginConfig() {
        return PluginConfig;
    }

    public static PortalBlocker getInstance() {
        return Instance;
    }

    private void RegisterCommands() {
        this.getCommand("portalblocker").setExecutor(DefaultCommandHandler);
    }

    private void RegisterEventHandlers() {
        EventHandler<CommandSender> configReloadRequestedEventHandler = new EventHandler<>() {
            @Override
            public void Handle(CommandSender eventArg) {
                LoadConfiguration();
                PluginDescriptionFile desc = getDescription();
                String msg = desc.getName() + " configuration reloaded";
                eventArg.sendMessage(msg);
            }
        };

        DefaultCommandHandler.ConfigReloadRequestedEvent.AddListener(configReloadRequestedEventHandler);

        this.getServer().getPluginManager().registerEvents(new BukkitEventListener(), this);
    }

    private void LoadConfiguration() {
        this.saveDefaultConfig();
        reloadConfig();
        FileConfiguration fileConfig = getConfig();
        boolean entitiesCanCreatePortals = fileConfig.getBoolean("entities-can-create-portals", false);
        String denyActionMessage = fileConfig.getString("deny-action-message", "You can't do that");

        this.PluginConfig = new PluginConfiguration(denyActionMessage, entitiesCanCreatePortals);
    }
}
