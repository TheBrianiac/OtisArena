package me.otisdiver.otisarena.task.kit;

import org.bukkit.entity.Player;

import me.otisdiver.otisarena.utils.LocationUtils;

public class Groundslam extends Ability {
    
    private static final long waitMillis = 7000;

    @Override
    public void run() {
        if (playerOnCooldown(player)) return;
        startCooldown(player, waitMillis);
        
        // for all players within 5 blocks of the ability user (player)
        for (Player target : main.getGame().getActivePlayers()) {
            if (!player.equals(target) && LocationUtils.withinDistance(player, target, 5)) {
                // apply knockback
                target.setVelocity(target.getLocation().getDirection().multiply(-1));
            }
        }
        
    }
    
    
    
}
