package de.jaunikapauni.axnpcs.command;

import de.jaunikapauni.axnpcs.AxNPCs;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

public class CreateCommand implements CommandExecutor {
    AxNPCs reference;
    public CreateCommand(AxNPCs reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        NamespacedKey npcKey = new NamespacedKey(reference, "npc");
        if(!p.hasPermission("axnpcs.create")){
            p.sendMessage("You don't have the permission! [axnpcs.create]");
            return true;
        }
        Villager npc = p.getWorld().spawn(p.getLocation(), Villager.class);

        npc.setAI(false);
        npc.setInvulnerable(true);
        if(args.length == 0){
            return false;
        }
        npc.setCustomName(args[0]);
        npc.setCustomNameVisible(true);
        npc.getPersistentDataContainer().set(npcKey, PersistentDataType.BYTE, (byte) 1);

        String id = UUID.randomUUID().toString();
        String path = "npcs."+ id;
        String cmd = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        reference.getConfig().set(path + ".name", args[0]);
        reference.getConfig().set(path + ".location", p.getLocation());
        reference.getConfig().set(path + ".command", cmd);
        reference.saveConfig();

        p.sendMessage("NPC created and saved!");
        return true;
    }
}
