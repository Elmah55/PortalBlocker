package pl.elmah.portalblocker.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import pl.elmah.portalblocker.core.PluginConfiguration;
import pl.elmah.portalblocker.core.PortalBlocker;

/*
This class handles event invoked after nether portal creation
 */
public class PortalCreateListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onNetherPortalCreate(PortalCreateEvent e) {
        if (PortalCreateEvent.CreateReason.FIRE == e.getReason()) {
            //By default event is cancelled. Only certain players can create portal
            e.setCancelled(true);
            Player eventSourcePlayer = PortalBlocker.getInstance().getFirePlacePlayer();
            PluginConfiguration config = PortalBlocker.getInstance().getPluginConfig();

            //Portal ignited by player
            if (null != eventSourcePlayer) {
                if (eventSourcePlayer.isOp() || eventSourcePlayer.hasPermission(PortalBlocker.PERMISSION_ENABLE_PORTAL_CREATION)) {
                    e.setCancelled(false);
                } else {
                    eventSourcePlayer.sendMessage(config.getDenyActionMessage());
                }

                PortalBlocker.getInstance().setFirePlacePlayer(null);
            }
            //Portal ignited by entity
            else if (true == config.getEntitiesCanCreatePortals()) {
                e.setCancelled(false);
            }
        }
    }
}
