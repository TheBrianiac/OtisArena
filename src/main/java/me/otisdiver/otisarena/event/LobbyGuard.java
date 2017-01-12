package me.otisdiver.otisarena.event;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.otisdiver.otisarena.OtisArena;

public class LobbyGuard extends Event {
    
    private final String lobbyName;
    
    public LobbyGuard(OtisArena main) {
        super(main);
        lobbyName = main.getGame().getLobby();
    }
    
    // Event handlers
    
    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent e) {
        if (inLobby(e.getPlayer())) cancel(e);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onHunger(FoodLevelChangeEvent e) {
        if (inLobby(e.getEntity())) cancel(e);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent e) {
        if (inLobby(e.getEntity())) cancel(e);
    }
    
    // Super lazy shortcut methods
    
    private boolean inLobby(Entity p) {
        return p.getWorld().getName().equals(lobbyName);
    }
    
    private void cancel(Cancellable e) {
        e.setCancelled(true);
    }
    
}
