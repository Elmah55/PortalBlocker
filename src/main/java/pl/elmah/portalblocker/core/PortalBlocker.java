package pl.elmah.portalblocker.core;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import pl.elmah.portalblocker.event.BlockIgniteListener;
import pl.elmah.portalblocker.event.EventHandler;
import pl.elmah.portalblocker.event.PortalCreateListener;

public class PortalBlocker extends JavaPlugin {
    public static final String PERMISSION_ENABLE_PORTAL_CREATION = "portalblocker.enable";

    private static PortalBlocker Instance;
    /* Stores most recent player that use that used lighter (flint and steel).
    Because spigot API does not allow accessing player that ignited the portal
    this field will be used to store player that used lighter and then in portal
    create event this will be used as player that created portal
     */
    private Player FirePlacePlayer;
    private PluginConfiguration PluginConfig;
    private CommandHandler DefaultCommandHandler = new CommandHandler();

    @Override
    public void onEnable() {
        Instance = this;

        RegisterEventHandlers();
        RegisterCommands();
        LoadConfiguration();

        PluginDescriptionFile desc = getDescription();
        String initInfo = String.format("%s version %s enabled",
                desc.getName(),
                desc.getVersion());
        getLogger().info(initInfo);
    }

    public Player getFirePlacePlayer() {
        return FirePlacePlayer;
    }

    public void setFirePlacePlayer(Player firePlacePlayer) {
        FirePlacePlayer = firePlacePlayer;
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

        this.getServer().getPluginManager().registerEvents(new PortalCreateListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockIgniteListener(), this);
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
