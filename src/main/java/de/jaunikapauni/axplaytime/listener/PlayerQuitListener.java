package de.jaunikapauni.axplaytime.listener;

import de.jaunikapauni.axplaytime.AxPlayTime;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {
    AxPlayTime reference;
    public PlayerQuitListener(AxPlayTime reference){
        this.reference = reference;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            long sessionTime = reference.getPlayTimeManager().getDelta(uuid);
            long newTotal = reference.getPlayTimeManager().getPlaytime().getOrDefault(uuid, 0L) + sessionTime;
            reference.getPlayTimeManager().savePlaytimeDB(uuid, newTotal);
            reference.getPlayTimeManager().getPlaytime().remove(uuid);
            reference.getPlayTimeManager().getStartTime().remove(uuid);
        });
    }
}
