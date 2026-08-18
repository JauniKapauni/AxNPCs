package de.jaunikapauni.axnpcs;

import de.jaunikapauni.axnpcs.command.CreateCommand;
import de.jaunikapauni.axnpcs.command.RemoveCommand;
import de.jaunikapauni.axnpcs.listener.PlayerInteractEntityListener;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxNPCs extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getCommand("create").setExecutor(new CreateCommand(this));
        getCommand("remove").setExecutor(new RemoveCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
        loadNpcs();
        getLogger().info("");
        getLogger().info("----------------------------------------");
        getLogger().info("Name: " + getName());
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info(String.join("Authors: " + ", ", getDescription().getAuthors()));
        getLogger().info("----------------------------------------");
        getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadNpcs(){
        NamespacedKey npcKey = new NamespacedKey(this, "npc");
        for(World world : Bukkit.getServer().getWorlds()){
            for(Entity entity : world.getEntities()){
                if(entity.getPersistentDataContainer().has(npcKey, PersistentDataType.BYTE)){
                    entity.remove();
                }
            }
        }
        if(getConfig().getConfigurationSection("npcs") == null){
            return;
        }
        for(String key : getConfig().getConfigurationSection("npcs").getKeys(false)){
            String path = "npcs." + key + ".";

            World world = Bukkit.getWorld(getConfig().getString(path + "world"));
            if(world == null){
                continue;
            }
            Location loc = new Location(world, getConfig().getDouble(path + "x"), getConfig().getDouble(path + "y"), getConfig().getDouble(path + "z"), (float) getConfig().getDouble(path + "yaw"), (float) getConfig().getDouble(path + "pitch"));
            loc.getChunk().load();
            Chunk chunk = loc.getChunk();
            for(Entity entity2 : chunk.getEntities()){
                if(entity2.getPersistentDataContainer().has(npcKey, PersistentDataType.BYTE)){
                    entity2.remove();
                }
            }
            String name = getConfig().getString(path + "name");

            if(loc != null){
                Villager npc = loc.getWorld().spawn(loc, Villager.class);
                npc.setAI(false);
                npc.setInvulnerable(true);
                npc.setCustomName(name);
                npc.setCustomNameVisible(false);
                npc.getPersistentDataContainer().set(npcKey, PersistentDataType.BYTE, (byte) 1);
            }
        }
    }
}
