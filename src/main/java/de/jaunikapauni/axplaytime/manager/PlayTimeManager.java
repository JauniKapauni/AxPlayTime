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
import java.util.concurrent.ConcurrentHashMap;

public class PlayTimeManager {
    AxPlayTime reference;
    public PlayTimeManager(AxPlayTime reference){
        this.reference = reference;
    }

    Map<UUID, Long> playtime = new ConcurrentHashMap<>();
    Map<UUID, Long> startTime = new ConcurrentHashMap<>();

    public Map<UUID, Long> getPlaytime(){
        return playtime;
    }
    public Map<UUID, Long> getStartTime(){
        return startTime;
    }

    public long getDelta(UUID uuid){
        return System.currentTimeMillis() - startTime.getOrDefault(uuid, System.currentTimeMillis());
    }

    public void savePlaytimeDB(UUID uuid, long totalSave){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement select = conn.prepareStatement("SELECT uuid FROM players WHERE uuid = ?")){
                select.setString(1, uuid.toString());
                ResultSet rs = select.executeQuery();
                if(rs.next()){
                    try(PreparedStatement update = conn.prepareStatement("UPDATE players SET playtime = ? WHERE uuid = ?")){
                        update.setLong(1, totalSave);
                        update.setString(2, uuid.toString());
                        update.executeUpdate();
                    }
                } else {
                    try(PreparedStatement insert = conn.prepareStatement("INSERT INTO players (uuid, playtime) VALUES (?, ?)")){
                        insert.setString(1, uuid.toString());
                        insert.setLong(2, totalSave);
                        insert.executeUpdate();
                    }
                }

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

    public long getPlayTimeMap(UUID uuid){
        return playtime.getOrDefault(uuid, 0L);
    }

    public long getCurrentSessionTime(UUID uuid){
        return System.currentTimeMillis() - startTime.getOrDefault(uuid, System.currentTimeMillis());
    }

    public long getTotalPlayTime(UUID uuid){
        long saved = getPlayTimeMap(uuid);
        long session = getCurrentSessionTime(uuid);
        return saved + session;
    }

    public void saveAll(){
        for(UUID uuid : playtime.keySet()){
            long total = getTotalPlayTime(uuid);
            savePlaytimeDB(uuid, total);
        }
    }
}
