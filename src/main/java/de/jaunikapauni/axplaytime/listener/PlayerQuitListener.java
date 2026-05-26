package de.jaunikapauni.axplaytime.listener;

import de.jaunikapauni.axplaytime.AxPlayTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    AxPlayTime reference;
    public PlayerQuitListener(AxPlayTime reference){
        this.reference = reference;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        reference.getPlayTimeManager().getEndTime().put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        reference.getPlayTimeManager().savePlaytimeDB(e.getPlayer());
    }
}
