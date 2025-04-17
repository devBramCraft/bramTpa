# bramTPA Plugin

**bramTPA** is a Minecraft plugin that provides teleportation commands for players. It allows players to send teleport requests, accept or deny them, and provides an admin command to reload the plugin configuration.

## Features

- **Player Commands**:
    - `/tpa <player>`: Send a teleport request to another player.
    - `/tpahere <player>`: Ask another player to teleport to you.
    - `/tpaccept`: Accept a teleport request.
    - `/tpdeny`: Deny a teleport request.
- **Admin Command**:
    - `/bramtpa reload`: Reload the plugin configuration.

## Permissions

- `bramtpa.admin`: Required to use the `/bramtpa reload` command.

## Installation

1. Download the plugin JAR file.
2. Place the JAR file in the `plugins` folder of your Minecraft server.
3. Restart the server to load the plugin.

## Configuration

The plugin uses a `config.yml` file for configuration. You can reload the configuration without restarting the server using the `/bramtpa reload` command.

## Development

### Technologies Used

- **Language**: Java
- **Build Tool**: Maven
- **Framework**: Bukkit/Spigot API

### Building the Project

1. Clone the repository.
2. Run `mvn clean package` to build the plugin.
3. The compiled JAR file will be located in the `target` directory.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.