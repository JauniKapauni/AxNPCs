package de.jaunikapauni.axnpcs.command;

import de.jaunikapauni.axnpcs.AxNPCs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CreateCommand implements CommandExecutor {
    AxNPCs reference;
    public CreateCommand(AxNPCs reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player p = (Player) sender;
        Villager npc = p.getWorld().spawn(p.getLocation(), Villager.class);

        npc.setAI(false);
        npc.setInvulnerable(true);
        if(args.length == 0){
            return false;
        }
        npc.setCustomName(args[0]);
        npc.setCustomNameVisible(true);

        String id = UUID.randomUUID().toString();
        String path = "npcs."+ id;

        reference.getConfig().set(path + ".name", args[0]);
        reference.getConfig().set(path + ".location", p.getLocation());
        reference.saveConfig();

        p.sendMessage("NPC created and saved!");
        return true;
    }
}
