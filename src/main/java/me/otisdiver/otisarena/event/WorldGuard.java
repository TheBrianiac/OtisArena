package me.otisdiver.otisarena.event;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import me.otisdiver.otisarena.OtisArena;

public class WorldGuard extends EasyListener {

    /** WorldGuard prevents players from editing the world.
     * 
     * @param main instance of JavaPlugin
     */
    public WorldGuard(OtisArena main) {
        super(main);
    }
    
    private void event(Player player, Cancellable e) {
        // if the player isn't in creative, cancel the event
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        event(e.getPlayer(), e);
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        event(e.getPlayer(), e);
    }
    
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onInvOpen(InventoryOpenEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        Player player = (Player) e.getPlayer();
        event(player, e);
    }

}
