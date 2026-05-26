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
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        long totalSaved = reference.getPlayTimeManager().getPlaytime().getOrDefault(uuid, 0L);

        long sessionStart = reference.getPlayTimeManager().getStartTime().getOrDefault(uuid, System.currentTimeMillis());
        long currentSessionTime = System.currentTimeMillis() - sessionStart;
        long totalPlaytime = totalSaved + currentSessionTime;
        p.sendMessage("Your playtime: " + (totalPlaytime / 1000) + "s");
        return true;
    }
}
