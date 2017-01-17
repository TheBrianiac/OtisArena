package me.otisdiver.otisarena.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.GameState;
import me.otisdiver.otisarena.task.Respawn;

public class GameDeath extends EasyListener {
    
    private final String killMessage = ChatColor.GRAY + "%s" + ChatColor.GRAY + " killed " + ChatColor.GREEN + "%s" + ChatColor.GRAY + "!";
    
    /** GameDeath handles deaths and kills during games.
     * 
     * @param main instance of JavaPlugin
     */
    public GameDeath(OtisArena main) {
        super(main);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        // conditions (we only care about players during the game)
        if (!(e.getEntity() instanceof Player)) return;
        if (!GameState.getCurrent().equals(GameState.PLAYING)) return;
        
        Player victim = (Player) e.getEntity();
        
        // if the attack wasn't fatal, stop
        double health = victim.getHealth() - e.getFinalDamage();
        if (health > 0) return;
        
        // artificial death (override Minecraft)
        e.setCancelled(true);
        victim.setGameMode(GameMode.SPECTATOR);
        new Respawn(main, victim).runFuture(100);
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e) {
        // conditions (we only care about players during the game)
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) return;
        if (!GameState.getCurrent().equals(GameState.PLAYING)) return;
        
        // find the attacker and victim
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        
        // if the attack wasn't fatal, stop
        double health = victim.getHealth() - e.getFinalDamage();
        if (health > 0) return;
        
        // point!
        Bukkit.broadcastMessage(String.format(killMessage, attacker.getName(), victim.getName()));
        main.getGame().playerScore(attacker);
    }
    
}
