package me.otisdiver.otisarena.task.kit;

import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.utils.LocationUtils;

public class Groundslam extends Ability {
    
    private static final long waitMillis = 7000;
    
    /** Activates a Groundslam ability for the given player.
     * 
     * @param main instance of JavaPlugin
     * @param player who used the ability
     */
    public Groundslam(OtisArena main, Player player) {
        super(main, player);
    }

    @Override
    public void run() {
        if (!super.registerUse(this.getClass(), waitMillis)) return;
        
        // for all players within 5 blocks of the ability user (player)
        for (Player target : game.getActivePlayers()) {
            if (LocationUtils.withinDistance(player, target, 5)) {
                // apply knockback
                target.setVelocity(target.getLocation().getDirection().multiply(-2));
            }
        }
        
    }
    
    
    
}
