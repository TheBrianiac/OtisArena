package me.otisdiver.otisarena.event;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import me.otisdiver.otisarena.OtisArena;

public class WorldGuard extends EasyListener {

    /** WorldGuard prevents players from editing the world.
     * 
     * @param main instance of JavaPlugin
     */
    public WorldGuard(OtisArena main) {
        super(main);
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        
        // if a player isn't in creative, cancel their edits
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

}
