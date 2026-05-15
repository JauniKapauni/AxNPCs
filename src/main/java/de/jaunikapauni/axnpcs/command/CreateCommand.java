package de.jaunikapauni.axnpcs.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

public class CreateCommand implements CommandExecutor {
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
        return true;
    }
}
