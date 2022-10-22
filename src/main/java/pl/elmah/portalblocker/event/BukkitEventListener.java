package pl.elmah.portalblocker.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.world.PortalCreateEvent;
import pl.elmah.portalblocker.core.PermissionManager;
import pl.elmah.portalblocker.core.PermissionType;
import pl.elmah.portalblocker.core.PluginConfiguration;
import pl.elmah.portalblocker.core.PortalBlocker;

/**
 * Main class for handling bukkit API events
 */
public class BukkitEventListener implements Listener {
    /**
     * Stores most recent player that use that used lighter (flint and steel).
     * Because bukkit API does not allow accessing player that ignited the portal
     * this field will be used to store player that used ignited obsidian block
     * and then in portal create event this will be used as player that created portal
     */
    private Player FirePlacePlayer;

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent e) {
        FirePlacePlayer = e.getPlayer();
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onNetherPortalCreate(PortalCreateEvent e) {
        if (PortalCreateEvent.CreateReason.FIRE == e.getReason()) {
            //By default event is cancelled. Only certain players can create portal
            e.setCancelled(true);
            Player eventSourcePlayer = FirePlacePlayer;
            PluginConfiguration config = PortalBlocker.getInstance().getPluginConfig();

            //Portal ignited by player
            if (null != eventSourcePlayer) {
                if (PermissionManager.HasPermission(
                        eventSourcePlayer, PermissionType.NETHER_PORTAL_CREATION, true)) {
                    e.setCancelled(false);
                }

                FirePlacePlayer = null;
            }
            //Portal ignited by entity
            else if (true == config.getEntitiesCanCreatePortals()) {
                e.setCancelled(false);
            }
        }
    }
}
