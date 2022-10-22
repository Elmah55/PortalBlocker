package pl.elmah.portalblocker.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import pl.elmah.portalblocker.event.EventSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Command handler for base command of plugin. Other commands are invoked by using base command with arguments
 */
public class CommandHandler implements CommandExecutor {
    public final EventSource<CommandSender> ConfigReloadRequestedEvent = new EventSource<>();

    private final static Map<String, Method> CommandHandlerMethods = new HashMap<>();
    private final static String CommandUsageMsg;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            Method cmdMethod = CommandHandlerMethods.get(args[0]);

            if (null == cmdMethod) {
                OnCommandNotFound(sender);
            } else {
                String[] newArgs;

                if (args.length > 1) {
                    newArgs = new String[args.length - 1];
                    Arrays.copyOfRange(args, 1, newArgs.length - 1);
                } else {
                    newArgs = new String[0];
                }

                try {
                    cmdMethod.invoke(this, sender, label, newArgs);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else {
            OnCommandNotFound(sender);
        }

        return true;
    }

    private void versionCommandHandler(CommandSender sender, String label, String[] args) {
        if (true == PermissionManager.HasPermission(sender, PermissionType.COMMAND_VERSION, true)) {
            PluginDescriptionFile desc = PortalBlocker.getInstance().getDescription();
            String versionMsg = String.format("%s by %s. Version %s",
                    desc.getName(),
                    desc.getAuthors().get(0),
                    desc.getVersion());

            sender.sendMessage(versionMsg);
        }
    }

    private void reloadCommandHandler(CommandSender sender, String label, String[] args) {
        if (true == PermissionManager.HasPermission(sender, PermissionType.COMMAND_RELOAD, true)) {
            ConfigReloadRequestedEvent.Invoke(sender);
        }
    }

    private void OnCommandNotFound(CommandSender sender) {
        sender.sendMessage(CommandUsageMsg);
    }

    /**
     * Returns command handler method object from method name
     *
     * @param methodName Name of method
     * @return Method object representing method with provided name
     */
    private static Method GetCommandMethod(String methodName) {
        Method resultMethod = null;

        try {
            resultMethod = CommandHandler.class.getDeclaredMethod(methodName,
                    CommandSender.class,
                    String.class,
                    String[].class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return resultMethod;
    }

    static {
        CommandHandlerMethods.put("version", GetCommandMethod("versionCommandHandler"));
        CommandHandlerMethods.put("reload", GetCommandMethod("reloadCommandHandler"));

        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("Unknown command. Usage: /portalblocker <");

        Iterator<Map.Entry<String, Method>> it = CommandHandlerMethods.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Method> methodEntry = it.next();
            msgBuilder.append(methodEntry.getKey());
            msgBuilder.append(it.hasNext() ? " | " : ">");
        }

        CommandUsageMsg = msgBuilder.toString();
    }
}