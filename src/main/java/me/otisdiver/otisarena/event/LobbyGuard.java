package me.otisdiver.otisarena.event;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.otisdiver.otisarena.OtisArena;

public class LobbyGuard extends EasyListener {
    
    private final String lobbyName;
    
    /** LobbyGuard stops human damage in the lobby.
     * 
     * @param main instance of JavaPlugin
     */
    public LobbyGuard(OtisArena main) {
        super(main);
        lobbyName = main.getGame().getLobby();
    }
    
    // Event handlers
    
    @EventHandler(priority = EventPriority.LOW)
    public void onHunger(FoodLevelChangeEvent e) {
        if (inLobby(e.getEntity())) cancel(e);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent e) {
        if (inLobby(e.getEntity())) cancel(e);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onDrop(PlayerDropItemEvent e) {
        if (inLobby(e.getPlayer())) cancel(e);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onInvEdit(InventoryClickEvent e) {
        if (inLobby(e.getWhoClicked())) cancel(e);
    }
    
    // Super lazy shortcut methods
    
    private boolean inLobby(Entity p) {
        return p.getWorld().getName().equals(lobbyName);
    }
    
    private void cancel(Cancellable e) {
        e.setCancelled(true);
    }
    
}
