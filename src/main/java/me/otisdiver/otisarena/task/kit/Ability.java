package me.otisdiver.otisarena.task.kit;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;

public abstract class Ability {

    protected static OtisArena main;
    private static boolean initiated = false;
    private static HashMap<Class<? extends Ability>, Long> lastUses = new HashMap<>();
    
    protected Player player;
    
    /** Artificial constructor.
     * 
     * @param argMain instance of JavaPlugin
     */
    public static void initiate(OtisArena argMain) {
        if (initiated) return;
        
        main = argMain;
        
        // flag class as initialized
        initiated = true;
    }
    
    /** Runs the ability for the given player.
     * 
     * @param player who used the ability
     */
    public void playerUse(Player player) {
        this.player = player;
        run();
    }
    
    protected abstract void run();
    
    /** Checks if a player too recently used the ability and updates the cooldown records.
     * 
     * @param ability the type of ability
     * @param waitMillis how long the player must wait since last use
     * @return true if player is allowed to use the ability
     */
    protected static boolean registerUse(Class<? extends Ability> ability, long waitMillis) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUses.get(ability) < waitMillis) return false;
        
        lastUses.put(ability, currentTime);
        return true;
    }

}
