package de.jaunikapauni.axplaytime.listener;

import de.jaunikapauni.axplaytime.AxPlayTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    AxPlayTime reference;
    public PlayerJoinListener(AxPlayTime reference){
        this.reference = reference;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        long saved = reference.getPlayTimeManager().loadPlayerPlaytime(uuid);
        reference.getPlayTimeManager().getPlaytime().put(uuid, saved);
        reference.getPlayTimeManager().getStartTime().put(uuid, System.currentTimeMillis());
    }
}
