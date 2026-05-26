package de.jaunikapauni.axplaytime;

import de.jaunikapauni.axplaytime.manager.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxPlayTime extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        try{
            databaseManager = new DatabaseManager(this);
            if(databaseManager.initDatabaseTable1()){
                Bukkit.getLogger().severe("Failed to create db table!");
                Bukkit.getServer().shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
