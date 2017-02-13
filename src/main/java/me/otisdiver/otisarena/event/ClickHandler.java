package me.otisdiver.otisarena.event;

import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.game.GameState;
import me.otisdiver.otisarena.game.Kit;

public class ClickHandler extends EasyListener {
    
    private static final String errorChat = ChatColor.DARK_RED + "Internal error! Check console or contact a developer.";
    private WeakHashMap<Player, ItemStack> activeButtons = new WeakHashMap<>();
    
    private Game game;
    
    public ClickHandler(OtisArena main) {
        super(main);
        game = main.getGame();
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(PlayerInteractEvent e) {
        // only pay attention to right click events with the main hand
        Action act = e.getAction();
        if (!(act.equals(Action.RIGHT_CLICK_AIR) || act.equals(Action.RIGHT_CLICK_BLOCK))) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        // check if the item is a kit button, assign kit if it is
        if (item.getType().equals(Kit.buttonDefault) && item.getItemMeta().hasDisplayName()) {
            String name = item.getItemMeta().getDisplayName();
            
            Kit[] kits = Kit.values();
            for(int i = 0; i < kits.length; i++) {
                
                // if the item name is the same as the kit's button
                Kit kit = kits[i];
                if (name.equals(kit.getDisplayName())) {
                    game.setKit(player, kit);
                    ItemStack oldButton = activeButtons.get(player);
                    if (oldButton != null) oldButton.setType(Kit.buttonDefault);
                    item.setType(Kit.buttonChosen);
                    activeButtons.put(player, item);
                    e.setCancelled(true);
                }
            }
            return;
        }
        
        // check if the item is a chosen kit button, reset kit if it is
        else if (item.getType().equals(Kit.buttonChosen)) {
            // if the button is for the player's chosen kit, unset their kit
            if (item.equals(activeButtons.get(player))) {
                item.setType(Kit.buttonDefault);
                activeButtons.remove(player);
                game.setKit(player, null);
                e.setCancelled(true);
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
 