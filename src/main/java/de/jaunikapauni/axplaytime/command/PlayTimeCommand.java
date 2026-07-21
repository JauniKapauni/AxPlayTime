package de.jaunikapauni.axplaytime.command;

import de.jaunikapauni.axplaytime.AxPlayTime;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayTimeCommand implements CommandExecutor {
    AxPlayTime reference;
    public PlayTimeCommand(AxPlayTime reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axplaytime.playtime")){
            p.sendMessage("You don't have the permission! [axplaytime.playtime]");
            return true;
        }
        UUID uuid = p.getUniqueId();
        long totalPlaytime = reference.getPlayTimeManager().getTotalPlayTime(uuid);
        p.sendMessage("Your playtime: " + reference.getPlayTimeManager().formatPlaytime(totalPlaytime));
        return true;
    }
}
