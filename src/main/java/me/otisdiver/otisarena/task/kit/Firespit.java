package me.otisdiver.otisarena.task.kit;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;

public class Firespit extends Ability {
    
    private static final long waitMillis = 3000;
    
    public Firespit(OtisArena main, Player player) {
        super(main, player);
    }

    @Override
    public void run() {
        if (!super.registerUse(this.getClass(), waitMillis)) return;
        
        // shoot a fireball out of the player
        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setVelocity(player.getLocation().getDirection().multiply(10));
    }
    
}
