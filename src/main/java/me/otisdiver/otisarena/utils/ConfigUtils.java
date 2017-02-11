package me.otisdiver.otisarena.utils;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import me.otisdiver.otisarena.OtisArena;

public class ConfigUtils {
    
    private static OtisArena main;
    
    /** Artificial constructor.
     * 
     * @param argMain instance of JavaPlugin
     */
    public static void init(OtisArena argMain) {
        main = argMain;
        // create a config if one doesn't exist
        main.saveDefaultConfig();
    }
    
    public static void reload() {
        main.reloadConfig();
    }
    
    public static FileConfiguration getConfig() {
        return main.getConfig();
    }
    
    private static String randomKey(Set<String> set) {
        
        // choose a random index, then find it from the set
        int random = RandUtils.rand(set.size());
        int i = 0;
        for(String item : set) {
            if (i == random) return item;
            i++;
        }
        
        // dead code (unless I did something stupid)
        return null;
    }
    
    public static String getRandomWorld() {
        
        // get all items under the section worlds
        Set<String> worlds = getConfig().getConfigurationSection("worlds").getKeys(false);
        
        return randomKey(worlds);
    }
    
    public static Location getRandomSpawn(World world) {
        
        // get the config section worlds.[world].spawns
        StringBuilder path = new StringBuilder();
        path.append("worlds.").append(world.getName()).append(".spawns");
        ConfigurationSection locations = getConfig().getConfigurationSection(path.toString());
        
        // find a set of coords from the config
        String spawn = randomKey(locations.getKeys(false));
        
        // build a Location object
        Double x = locations.getDouble(spawn + ".x");
        Double y = locations.getDouble(spawn + ".y");
        Double z = locations.getDouble(spawn + ".z");
        return new Location(world, x, y, z);
    }
    
}
