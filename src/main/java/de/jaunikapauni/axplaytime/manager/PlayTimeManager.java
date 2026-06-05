package de.jaunikapauni.axplaytime.manager;

import de.jaunikapauni.axplaytime.AxPlayTime;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Map<UUID, Long> getPlaytime(){
        return playtime;
    }
    public Map<UUID, Long> getStartTime(){
        return startTime;
    }

    public long getDelta(Player p){
        return System.currentTimeMillis() - startTime.get(p.getUniqueId());
    }

    public void savePlaytimeDB(Player p){
        long totalSave = playtime.getOrDefault(p.getUniqueId(), 0L);
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("REPLACE INTO players(uuid, playtime) VALUES (?, ?)")){
                ps.setString(1, p.getUniqueId().toString());
                ps.setLong(2, totalSave);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long loadPlayerPlaytime(UUID uuid){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT playtime FROM players WHERE uuid = ?")){
                ps.setString(1, uuid.toString());
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return rs.getLong("playtime");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    public String formatPlaytime(long millis){
        long seconds = millis / 1000;

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        return hours + "h " + minutes + "m " + remainingSeconds + "s";
    }
}
