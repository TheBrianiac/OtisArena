package me.otisdiver.otisarena.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.task.Respawn;

public class Kill extends Event {
    
    private final String deathMessage = ChatColor.GRAY + "%s" + ChatColor.GRAY + " killed " + ChatColor.GREEN + "%s" + ChatColor.GRAY + "!";
    
    public Kill(OtisArena main) {
        super(main);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e) {
        
        // if either entity involved wasn't a player, stop
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
            return;
        }
        
        // find the attacker and victim
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        
        // if the attack wasn't fatal, stop
        double health = victim.getHealth() - e.getFinalDamage();
        if (health > 0) return;
        
        // artificial death (override Minecraft)
        e.setCancelled(true);
        Bukkit.broadcastMessage(String.format(deathMessage, attacker.getName(), victim.getName()));
        victim.setGameMode(GameMode.SPECTATOR);
        new Respawn(main, victim).runFuture(100);
        
        // point!
        main.getGame().playerScore(attacker);
    }
    
}
