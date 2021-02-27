package pl.elmah.portalblocker;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalBlocker extends JavaPlugin {
    private static PortalBlocker Instance;
    private final String Version = "2.0";
    /* Stores most recent player that use that used lighter (flint and steel).
    Because spigot API does not allow to access player that ignited the portal
    this field will be used to store player that used lighter and then in portal
    create event this will be used as player that created portal
     */
    private Player FirePlacePlayer;

    public static final String ENABLE_PORTAL_CREATION_PERMISSION = "portalblocker.enable";

    @Override
    public void onEnable() {
        Instance = this;
        this.getServer().getPluginManager().registerEvents(new PortalCreateListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getLogger().info("PortalBlocker version " + Version + " initialized");
    }

    public Player getFirePlacePlayer() {
        return FirePlacePlayer;
    }

    public void setFirePlacePlayer(Player firePlacePlayer) {
        FirePlacePlayer = firePlacePlayer;
    }

    public static PortalBlocker getInstance() {
        return Instance;
    }
}
