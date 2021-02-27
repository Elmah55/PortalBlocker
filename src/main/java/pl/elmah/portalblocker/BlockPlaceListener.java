package pl.elmah.portalblocker;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (Material.FIRE == e.getBlock().getType()) {
            PortalBlocker.getInstance().setFirePlacePlayer(e.getPlayer());
        }
    }
}
