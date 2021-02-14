package pl.elmah.portalblocker;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEventListener implements Listener {

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.OBSIDIAN) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            Location blockPlaceLoc = event.getBlockPlaced().getLocation();
            ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(blockPlaceLoc));
            RegionAssociable region = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());
            boolean testResult =
                    set.testState(region, PortalBlocker.getInstance().getObsidianPlaceFlag()) &&
                    set.testState(region, Flags.BUILD);
            testResult = testResult || event.getPlayer().isOp();
            event.setCancelled(!testResult);
        }
    }
}
