package pl.elmah.portalblocker.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import pl.elmah.portalblocker.core.PortalBlocker;

public class BlockIgniteListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent e) {
            PortalBlocker.getInstance().setFirePlacePlayer(e.getPlayer());
    }
}
