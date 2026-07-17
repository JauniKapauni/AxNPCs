package de.jaunikapauni.axnpcs.command;

import de.jaunikapauni.axnpcs.AxNPCs;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class RemoveCommand implements CommandExecutor {
    AxNPCs reference;
    public RemoveCommand(AxNPCs reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axnpcs.remove")){
            p.sendMessage("You don't have the permission! [axnpcs.remove]");
            return true;
        }
        Entity targetEntity = p.getTargetEntity(1);
        if(targetEntity != null){
            Location locOfTargetE = targetEntity.getLocation();
            if(reference.getConfig().getConfigurationSection("npcs") == null){
                return true;
            }
            for(String key : reference.getConfig().getConfigurationSection("npcs").getKeys(false)){
                String path = "npcs." + key;
                Location loc = reference.getConfig().getLocation(path + ".location");
                if(loc.distanceSquared(locOfTargetE) < 0.1){
                    reference.getConfig().set(path, null);
                    reference.saveConfig();
                    NamespacedKey npcKey = new NamespacedKey(reference, "npc");
                    if(targetEntity.getPersistentDataContainer().has(npcKey, PersistentDataType.BYTE)){
                        targetEntity.remove();
                        break;
                    }
                }
            }
        }
        return true;
    }
}
