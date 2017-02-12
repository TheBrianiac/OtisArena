package me.otisdiver.otisarena.game;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.utils.ColorUtils;
import me.otisdiver.otisarena.utils.HotbarUtils;

public class InventoryBuilder {
    
    private OtisArena main;
    
    /** Gives player the proper inventory depending on the game state.
     * 
     * @param main instance of JavaPlugin
     * @param player player whose inventory will be edited
     */
    public InventoryBuilder(OtisArena main, Player player) {
        this.main = main;
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        
        switch(GameState.getCurrent()) {
            case RECRUITING:
                buildKitMenu(player);
                break;
            case UNLOADING:
                buildKitMenu(player);
                break;
            case LOADING:
                buildGameInv(player);
                break;
            case PLAYING:
                buildGameInv(player);
                break;
            default:
                break;
        }
        
    }
    
    /** Put kit selection buttons into the player's hotbar. */
    private void buildKitMenu(Player player) {
        Kit[] kits = Kit.values();
        int[] indexes = HotbarUtils.getHotbarIndexes(kits.length);
        for(int i = 0; i < kits.length; i++) {
            player.getInventory().setItem(indexes[i], kits[i].getNewButton());
        }
    }
    
    /** Give the player the game loadout. */
    private void buildGameInv(Player player) {
        player.getInventory().setContents(getLoadout());
        player.getInventory().setArmorContents(getArmor(player));
    }
    
    /** Standard set of items to give players in the game. */
    private ItemStack[] getLoadout() {
        return new ItemStack[] {
            new ItemStack(Material.STONE_SWORD),
            new ItemStack(Material.APPLE, 64),
            new ItemStack(Material.BOW),
            new ItemStack(Material.ARROW, 10)
        };
    }
    
    /** Armor for the players, colored according to their team. */
    private ItemStack[] getArmor(Player player) {
        // create a colored shirt
        ItemStack shirt = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) shirt.getItemMeta();
        Color armorColor = ColorUtils.fromChatColor(main.getGame().getPlayerTeam(player).getColor(), Color.WHITE);
        meta.setColor(armorColor);
        shirt.setItemMeta(meta);
        
        // put the shirt with a set of chain armor, return
        return new ItemStack[] {
            new ItemStack(Material.CHAINMAIL_BOOTS),
            new ItemStack(Material.CHAINMAIL_LEGGINGS),
            shirt,
            new ItemStack(Material.CHAINMAIL_HELMET),
        };
    }
    
}
