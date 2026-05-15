package de.jaunikapauni.axnpcs;

import de.jaunikapauni.axnpcs.command.CreateCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxNPCs extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("create").setExecutor(new CreateCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
