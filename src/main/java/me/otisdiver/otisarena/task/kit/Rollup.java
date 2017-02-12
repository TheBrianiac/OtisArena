package me.otisdiver.otisarena.task.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Rollup extends Ability {
    
    private static final Long waitMillis = 10000L;
    private static final String enableMessage = "" + ChatColor.GOLD + ChatColor.BOLD + "Shield enabled!";
    private static final String disableMessage = "" + ChatColor.GRAY + ChatColor.BOLD + "Shield disabled!";

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
                boolean toggle = !player.isInvulnerable();
                player.sendMessage(toggle ? enableMessage : disableMessage);
                player.setInvulnerable(toggle);
            }
        };
    }
}
