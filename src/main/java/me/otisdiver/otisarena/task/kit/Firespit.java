package me.otisdiver.otisarena.task.kit;

import org.bukkit.entity.Fireball;

public class Firespit extends Ability {
    
    private static final long waitMillis = 3000;

    @Override
    protected void run() {
        if (playerOnCooldown(player)) return;
        startCooldown(player, waitMillis);
        
        // shoot a fireball out of the player
        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setVelocity(player.getLocation().getDirection().multiply(10));
    }
    
}
