package me.otisdiver.otisarena.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.game.GameState;
import me.otisdiver.otisarena.game.Kit;

public class ClickHandler extends EasyListener {
    
    private static final String errorChat = ChatColor.DARK_RED + "Internal error! Check console or contact a developer.";
    
    private Game game;
    
    public ClickHandler(OtisArena main) {
        super(main);
        game = main.getGame();
    }
    
    // this event, despite its name, represents a right click with an item!
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        // check if the item is a kit button, assign kit if it is
        if (item.getType().equals(Kit.buttonDefault) && item.getItemMeta().hasDisplayName()) {
            String name = item.getItemMeta().getDisplayName();
            
            Kit[] kits = Kit.values();
            for(int i = 0; i < kits.length; i++) {
                
                // if the item name is the same as the kit's button
                if (name.equals(kits[i].getDisplayName())) {
                    game.setKit(player, kits[i]);
                    item.setType(Kit.buttonChosen);
                    e.setCancelled(true);
                }
            }
            return;
        }
        
        // check if the item is a kit tool and game is running, activate ability if so
        if (item.getType().equals(Kit.abilityTool) && GameState.getCurrent().equals(GameState.PLAYING)) {
            try {
                game.getKit(player).getAbility().newInstance().playerUse(player);
            } catch (Exception e1) {
                e1.printStackTrace();
                player.sendMessage(errorChat);
            }
            return;
        }
    }
    
}
 