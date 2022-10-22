package pl.elmah.portalblocker.core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for resolving permissions of server users
 */
public class PermissionManager {
    private static final String BasePermissionNode = "portalblocker";
    private static final String WildcardPermissionNode = BasePermissionNode + ".*";
    //Maps permission type to permission node string
    private static final Map<PermissionType, String> PermissionNodes = new HashMap<>();

    /**
     * Checks whether given user has permission
     *
     * @param sender              Permission of this sender will be checked
     * @param permission          Type of permission to be checked
     * @param sendNoPermissionMsg If set to true message will be sent to user if he does not have permission
     * @return True if provided sender has permission, false otherwise
     */
    public static boolean HasPermission(CommandSender sender, PermissionType permission, boolean sendNoPermissionMsg) {
        boolean result = false;

        if (null != sender) {
            if (true == sender.isOp() || sender.hasPermission(WildcardPermissionNode)) {
                result = true;
            } else if (sender instanceof Player) {
                String permissionNode = PermissionNodes.get(permission);
                result = sender.hasPermission(permissionNode);
            }

            if (false == result && true == sendNoPermissionMsg) {
                sender.sendMessage(PortalBlocker.getInstance().getPluginConfig().getDenyActionMessage());
            }
        }

        return result;
    }

    static {
        PermissionNodes.put(PermissionType.COMMAND_RELOAD, BasePermissionNode + ".reload");
        PermissionNodes.put(PermissionType.COMMAND_VERSION, BasePermissionNode + ".version");
        PermissionNodes.put(PermissionType.NETHER_PORTAL_CREATION, BasePermissionNode + ".enable");
    }
}
