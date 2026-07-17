package de.jaunikapauni.axnpcs.listener;

import de.jaunikapauni.axnpcs.AxNPCs;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteractEntityListener implements Listener {
    AxNPCs reference;
    public PlayerInteractEntityListener(AxNPCs reference){
        this.reference = reference;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        if(!p.hasPermission("axnpcs.use")){
            p.sendMessage("You don't have the permission! [axnpcs.use]");
            return;
        }
        Entity entity = e.getRightClicked();
        NamespacedKey npcKey = new NamespacedKey(reference, "npc");
        if(entity.getPersistentDataContainer().has(npcKey, PersistentDataType.BYTE)){
            e.setCancelled(true);
            if(reference.getConfig().getConfigurationSection("npcs") == null){
                return;
            }
            for(String key : reference.getConfig().getConfigurationSection("npcs").getKeys(false)){
                String path = "npcs." + key;
                Location loc = reference.getConfig().getLocation(path + ".location");
                if(loc.distanceSquared(entity.getLocation()) < 1.0){
                    String cmd = reference.getConfig().getString(path + ".command");
                    p.performCommand(cmd);
                }
            }
        }
    }
}
