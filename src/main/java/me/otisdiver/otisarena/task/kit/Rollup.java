package me.otisdiver.otisarena.task.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;

public class Rollup extends Ability {
    
    private static final int waitMillis = 10000;
    private static final String statusMessage = "" + ChatColor.GRAY + ChatColor.BOLD + "Shield activated!";

    public Rollup(OtisArena main, Player player) {
        super(main, player);
    }

    @Override
    public void run() {
        if (!super.registerUse(this.getClass(), waitMillis)) return;
        
        // toggle invincibility once, and then again in 3 seconds (20 * 3 = 60 ticks)
        Bukkit.getScheduler().runTask(main, toggleInvincibility());
        Bukkit.getScheduler().runTaskLater(main, toggleInvincibility(), 60);
    }
    
    private Runnable toggleInvincibility() {
        return new Runnable() {
            @Override
            public void run() {
                player.sendMessage(statusMessage);
                player.setInvulnerable(!player.isInvulnerable());
            }
        };
    }

}
