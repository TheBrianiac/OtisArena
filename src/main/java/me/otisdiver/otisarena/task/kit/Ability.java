package me.otisdiver.otisarena.task.kit;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;

public abstract class Ability {

    protected static OtisArena main;
    private static HashMap<Player, Long> cooldowns = new HashMap<>();
    
    protected Player player;
    
    /** Artificial constructor.
     * 
     * @param argMain instance of JavaPlugin
     */
    public static void init(OtisArena argMain) {
        main = argMain;
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
    
    protected boolean playerOnCooldown(Player player) {
        Long cooldown = cooldowns.get(player);
        if (cooldown == null) return false;
        return (cooldown >= System.currentTimeMillis());
    }
    
    protected void startCooldown(Player player, Long delayMillis) {
        cooldowns.put(player, System.currentTimeMillis() + delayMillis);
    }

}
