package pl.elmah.portalblocker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

/*
This class handles event invoked after nether portal creation
 */
public class PortalCreateListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onNetherPortalCreate(PortalCreateEvent e) {
        //By default event is cancelled. Only certain players can create portal
        e.setCancelled(true);
        Player eventSourcePlayer = PortalBlocker.getInstance().getFirePlacePlayer();

        if (PortalCreateEvent.CreateReason.FIRE == e.getReason() && null != eventSourcePlayer) {
            if (eventSourcePlayer.isOp() || eventSourcePlayer.hasPermission(PortalBlocker.ENABLE_PORTAL_CREATION_PERMISSION)) {
                e.setCancelled(false);
            }
        }
    }
}
