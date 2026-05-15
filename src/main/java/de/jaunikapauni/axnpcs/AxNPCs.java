package de.jaunikapauni.axnpcs;

import de.jaunikapauni.axnpcs.command.CreateCommand;
import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxNPCs extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getCommand("create").setExecutor(new CreateCommand(this));
        loadNpcs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadNpcs(){
        if(getConfig().getConfigurationSection("npcs") == null){
            return;
        }
        for(String key : getConfig().getConfigurationSection("npcs").getKeys(false)){
            String path = "npcs." + key + ".";

            Location loc = getConfig().getLocation(path + "location");
            String name = getConfig().getString(path + "name");

            if(loc != null){
                Villager npc = loc.getWorld().spawn(loc, Villager.class);
                npc.setAI(false);
                npc.setInvulnerable(true);
                npc.setCustomName(name);
            }
        }
    }
}
