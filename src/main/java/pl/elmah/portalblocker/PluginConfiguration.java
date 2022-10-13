package pl.elmah.portalblocker;

public class PluginConfiguration
{
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
