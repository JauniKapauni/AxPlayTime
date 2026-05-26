package de.jaunikapauni.axplaytime.listener;

import de.jaunikapauni.axplaytime.AxPlayTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    AxPlayTime reference;
    public PlayerJoinListener(AxPlayTime reference){
        this.reference = reference;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        reference.getPlayTimeManager().getStartTime().put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
    }
}
