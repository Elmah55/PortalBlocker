<p align="center">
<img src="assets/Logo.png" />
</p>

# PortalBlocker
Spigot plugin that prevents creating nether portals by players.

## Commands
- `/portalblocker reload` Reloads plugin configuration from config.yml file
- `/portalblocker version` Outputs PortalBlocker's version

## Permissions
By default only OPs have all permissions.

- `portalblocker.*` Gives player all permissions
- `portalblocker.enable` Allows player to create nether portal
- `portalblocker.reload` Enables `/portalblocker reload` command
- `portalblocker.version` Enables `/portalblocker version` command

## Configuration
Plugin is configured through `config.yml` file. Config file is located in `plugins\PortalBlocker`  
Following attributes can be configured:  
- `deny-action-message` Type: `string` Default value: `You can't do that`  
Message sent to player when he does not have permission to execute action
- `entities-can-create-portals` Type: `boolean` Default value: `false`  
Defines whether entities (lightning strike, blaze, etc.) can create nether portals

If config.yml file does not exsist it will be created when plugin is loaded. When attribute is not
found in config file default value specified above will be used.

## Building
PortalBlocker can be built using Maven. Use `mvn package` command from root folder of repository. Output .jar file will be placed in `target` directory. More information about using Maven can be found [here](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

## Notes
Plugin has been tested on Spigot 1.18.2 but should work on older versions as well.
