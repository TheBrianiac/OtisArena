package me.otisdiver.otisarena;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {
    
    private static OtisArena         main;
    private static FileConfiguration config;
    
    private static Random randomSet;
    
    private static boolean initiated = false;
    
    static void initiate(OtisArena mainClass) {
        
        if (initiated) return;
        
        // save the main class
        main = mainClass;
        
        // load the config
        config = main.getConfig();
        if (config == null) {
            // create a config.yml and load it again
            main.saveDefaultConfig();
            config = main.getConfig();
        }
        
        // make a random
        randomSet = new Random();
        
        // flag class as initiated
        initiated = true;
    }
    
    public static void reload() {
        main.reloadConfig();
    }
    
    public static FileConfiguration getConfig() {
        return config;
    }
    
    private static String randomKey(Set<String> set) {
        
        // choose a random index, then find it from the set
        int random = randomSet.nextInt(set.size());
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
        Set<String> worlds = config.getConfigurationSection("worlds").getKeys(false);
        
        return randomKey(worlds);
    }
    
    public static Location getRandomSpawn(World world) {
        
        String worldName = world.getName();
        
        // get the config section worlds.[world].spawns
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("worlds.").append(worldName).append(".spawns");
        String path = pathBuilder.toString();
        
        // find all the locations defined for the world
        ConfigurationSection locations = config.getConfigurationSection(path);
        
        // find a set of coords from the config
        String locationName;
        String pathCoords;
        List<Double> coords = null;
        
        boolean run = true;
        while(run) {
            run = false;
            
            // choose a random item from the set of keys
            locationName = randomKey(locations.getKeys(false));
            
            // get a list of doubles
            pathCoords = path + locationName;
            coords = locations.getDoubleList(pathCoords);
            
            // if the list is too big, choose a new one
            if (coords.size() != 3) {
                Bukkit.getLogger().warning("Invalid configuration value: " + pathCoords);
                run = true; // (run again)
            }
        }
        
        // assemble a location
        Double x = coords.get(1);
        Double y = coords.get(2);
        Double z = coords.get(3);
        
        return new Location(world, x, y, z);
    }
    
}
