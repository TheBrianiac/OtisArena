package me.otisdiver.otisarena.game;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.utils.ColorUtils;

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
        int[] indexes = getHotbarIndexes(kits.length);
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
    
    /** Positions in the inventory to place kit selection buttons. */
    private int[] getHotbarIndexes(int items) {
        switch(items) {
            case 9:
                return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
            case 8:
                return new int[] {0, 1, 2, 3, 5, 6, 7, 8};
            case 7:
                return new int[] {1, 2, 3, 4, 5, 6, 7};
            case 6:
                return new int[] {1, 2, 3, 5, 6, 7};
            case 5:
                return new int[] {0, 2, 4, 6, 8};
            case 4:
                return new int[] {1, 3, 5, 7};
            case 3:
                return new int[] {2, 4, 6};
            case 2:
                return new int[] {2, 6};
            case 1:
                return new int[] {4};
            default:
                return null;
        }
    }
    
}
