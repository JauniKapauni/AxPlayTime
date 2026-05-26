package de.jaunikapauni.axplaytime;

import de.jaunikapauni.axplaytime.command.PlayTimeCommand;
import de.jaunikapauni.axplaytime.listener.PlayerJoinListener;
import de.jaunikapauni.axplaytime.listener.PlayerQuitListener;
import de.jaunikapauni.axplaytime.manager.DatabaseManager;
import de.jaunikapauni.axplaytime.manager.PlayTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxPlayTime extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }
    PlayTimeManager playTimeManager;
    public PlayTimeManager getPlayTimeManager(){
        return playTimeManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        try{
            playTimeManager = new PlayTimeManager(this);
            databaseManager = new DatabaseManager(this);
            if(databaseManager.initDatabaseTable1()){
                Bukkit.getLogger().severe("Failed to create db table!");
                Bukkit.getServer().shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getCommand("playtime").setExecutor(new PlayTimeCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
