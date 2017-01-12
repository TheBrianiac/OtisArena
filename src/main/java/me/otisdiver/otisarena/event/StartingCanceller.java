package me.otisdiver.otisarena.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.GameState;

public class StartingCanceller extends Event {

    /** StartingCanceller cancels certain events during the gameplay count down.
     * 
     * @param main instance of JavaPlugin
     */
    public StartingCanceller(OtisArena main) {
        super(main);
    }
    
    // Method called by all event handlers in this class
    private void event(Cancellable e) {
        
        // cancel the event if the game is STARTING (count down before gameplay)
        if (GameState.getCurrent() == GameState.STARTING) {
            e.setCancelled(true);
        }
    }

    // Event handlers (just run the event through to the event method)
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) { event(e); }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) { event(e); }
}
