package me.otisdiver.otisarena.task.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Rollup extends Ability {
    
    private static final Long waitMillis = 10000L;
    private static final String statusMessage = "" + ChatColor.GRAY + ChatColor.BOLD + "Shield activated!";

    @Override
    public void run() {
        if (playerOnCooldown(player)) return;
        startCooldown(player, waitMillis);
        
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
