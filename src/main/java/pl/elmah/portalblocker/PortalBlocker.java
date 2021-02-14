package pl.elmah.portalblocker;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalBlocker extends JavaPlugin {

    private static PortalBlocker Instance;
    private final String version = "1.0";
    private StateFlag ObsidianPlaceFlag;
    private boolean FlagsRegistered;

    @Override
    public void onLoad() {
        FlagsRegistered = registerWorldGuardFlags();
    }

    @Override
    public void onEnable() {
        Instance = this;
        getLogger().info("PortalBlocker version " + version + " initialized");

        if (true == FlagsRegistered) {
            //Register events listeners
            this.getServer().getPluginManager().registerEvents(new BlockEventListener(), this);
        }

    }

    public static PortalBlocker getInstance() {
        return Instance;
    }

    public StateFlag getObsidianPlaceFlag() {
        return this.ObsidianPlaceFlag;
    }

    private boolean registerWorldGuardFlags() {
        boolean result = true;
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        StateFlag flag = new StateFlag("obsidian-place", true);

        try {
            registry.register(flag);
            this.ObsidianPlaceFlag = flag;
        } catch (FlagConflictException e) {
            result = false;
            this.getLogger().warning("Cannot register flag: " + flag.getName() +
                    " It is conflicting with flag registered by other plugin");
        }

        return result;
    }
}
