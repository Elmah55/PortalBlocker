package pl.elmah.portalblocker.core;

/**
 * Stores plugin configuration loaded from config.yml file
 */
public class PluginConfiguration {
    private String DenyActionMessage;
    private boolean EntitiesCanCreatePortals;

    public PluginConfiguration(String denyActionMessage, boolean entitiesCanCreatePortals) {
        this.DenyActionMessage = denyActionMessage;
        this.EntitiesCanCreatePortals = entitiesCanCreatePortals;
    }

    public String getDenyActionMessage() {
        return DenyActionMessage;
    }

    public boolean getEntitiesCanCreatePortals() {
        return EntitiesCanCreatePortals;
    }
}
