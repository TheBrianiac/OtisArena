package me.otisdiver.otisarena.task.kit;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.task.Task;

public abstract class Ability extends Task {

    protected Game game;
    protected Player player;
    
    public Ability(OtisArena main, Player player) {
        super(main);
        game = main.getGame();
        this.player = player;
    }
    
    private static HashMap<Class<? extends Ability>, Long> lastUses = new HashMap<>();
    
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
