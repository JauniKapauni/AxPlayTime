package de.jaunikapauni.axplaytime.manager;

import de.jaunikapauni.axplaytime.AxPlayTime;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayTimeManager {
    AxPlayTime reference;
    public PlayTimeManager(AxPlayTime reference){
        this.reference = reference;
    }
    Map<UUID, Long> playtime = new HashMap<>();
    Map<UUID, Long> startTime = new HashMap<>();
    Map<UUID, Long> endTime = new HashMap<>();

    public Map<UUID, Long> getPlaytime(){
        return playtime;
    }
    public Map<UUID, Long> getStartTime(){
        return startTime;
    }
    public Map<UUID, Long> getEndTime(){
        return endTime;
    }
    public Long getDelta(Player p){
        return endTime.get(p.getUniqueId()) - startTime.get(p.getUniqueId());
    }
    public void savePlaytimeDB(Player p){
        UUID uuid = p.getUniqueId();
        long sessionTime = getDelta(p);
        long currentTotal = playtime.getOrDefault(uuid, 0L);
        playtime.put(uuid, currentTotal + sessionTime);
    }
}
