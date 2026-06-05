package de.jaunikapauni.axplaytime.placeholder;

import de.jaunikapauni.axplaytime.AxPlayTime;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class PlayTimePlaceholder extends PlaceholderExpansion {
    AxPlayTime reference;
    public PlayTimePlaceholder(AxPlayTime reference){
        this.reference = reference;
    }
    @Override
    public @NotNull String getIdentifier() {
        return reference.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join("", reference.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return reference.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer p, @NotNull String params){
        if(params.equalsIgnoreCase("playtime")){
            //long playtime = reference.getPlayTimeManager().loadPlayerPlaytime(p.getUniqueId());
            //return reference.getPlayTimeManager().formatPlaytime(playtime);

            UUID uuid = p.getUniqueId();
            long totalSaved = reference.getPlayTimeManager().getPlaytime().getOrDefault(uuid, 0L);

            long sessionStart = reference.getPlayTimeManager().getStartTime().getOrDefault(uuid, System.currentTimeMillis());
            long currentSessionTime = System.currentTimeMillis() - sessionStart;
            long totalPlaytime = totalSaved + currentSessionTime;
            return reference.getPlayTimeManager().formatPlaytime(totalPlaytime);
        }
        return params;
    }
}
