package pl.elmah.portalblocker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalBlocker extends JavaPlugin {
    private static PortalBlocker Instance;
    /* Stores most recent player that use that used lighter (flint and steel).
    Because spigot API does not allow accessing player that ignited the portal
    this field will be used to store player that used lighter and then in portal
    create event this will be used as player that created portal
     */
    private Player FirePlacePlayer;
    private PluginConfiguration PluginConfig;

    public static final String PERMISSION_ENABLE_PORTAL_CREATION = "portalblocker.enable";

    @Override
    public void onEnable() {
        Instance = this;
        this.getServer().getPluginManager().registerEvents(new PortalCreateListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockIgniteListener(), this);
        this.PluginConfig = loadConfig();

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

    private PluginConfiguration loadConfig() {
        this.saveDefaultConfig();
        FileConfiguration fileConfig = this.getConfig();

        boolean entitiesCanCreatePortals = fileConfig.getBoolean("entities-can-create-portals", false);
        String denyActionMessage = fileConfig.getString("deny-action-message", "You can't do that");

        PluginConfiguration pluginConfig = new PluginConfiguration(denyActionMessage, entitiesCanCreatePortals);

        return pluginConfig;
    }
}
