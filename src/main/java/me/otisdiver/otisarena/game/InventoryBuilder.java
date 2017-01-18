package me.otisdiver.otisarena.game;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.otisdiver.otisarena.OtisArena;

public class InventoryBuilder {
    
    private OtisArena main;
    
    /** Gives player the proper inventory depending on the game state.
     * 
     * @param main instance of JavaPlugin
     * @param player player whose inventory will be edited
     */
    public InventoryBuilder(OtisArena main, Player player) {
        this.main = main;
        
        switch(GameState.getCurrent()) {
            case RECRUITING:
                Kit[] kits = Kit.values();
                int[] indexes = getHotbarIndexes(kits.length);
                for(int i = 0; i < kits.length; i++) {
                    player.getInventory().setItem(indexes[i], kits[i].getNewButton());
                }
                break;
            case LOADING:
                player.getInventory().clear();
                player.getInventory().setContents(getLoadout());
                player.getInventory().setArmorContents(getArmor(player));
                break;
            default:
                break;
        }
        
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
        meta.setColor(getArmorColor(meta.getColor(), player));
        shirt.setItemMeta(meta);
        
        // put the shirt with a set of chain armor, return
        return new ItemStack[] {
            new ItemStack(Material.CHAINMAIL_HELMET),
            shirt,
            new ItemStack(Material.CHAINMAIL_LEGGINGS),
            new ItemStack(Material.CHAINMAIL_BOOTS)
        };
    }
    
    /** Get an armor color based on the given player's team color. */
    private Color getArmorColor(Color color, Player player) {
        int[] rgb;
        ChatColor chat = main.getGame().getPlayerTeam(player).getColor();
        
        switch(chat) {
            case AQUA:
                rgb = new int[] {0, 230, 240};
                break;
            case BLUE:
                rgb = new int[] {0, 200, 225};
                break;
            case DARK_AQUA:
                rgb = new int[] {0, 200, 180};
                break;
            case DARK_BLUE:
                rgb = new int[] {0, 0, 225};
                break;
            case DARK_GRAY:
                rgb = new int[] {80, 80, 80};
                break;
            case DARK_GREEN:
                rgb = new int[] {0, 150, 0};
                break;
            case DARK_PURPLE:
                rgb = new int[] {90, 0, 150};
                break;
            case DARK_RED:
                rgb = new int[] {200, 50, 50};
                break;
            case GOLD:
                rgb = new int[] {255, 255, 0};
                break;
            case GRAY:
                rgb = new int[] {180, 180, 180};
                break;
            case GREEN:
                rgb = new int[] {0, 255, 0};
                break;
            case LIGHT_PURPLE:
                rgb = new int[] {255, 100, 255};
                break;
            case RED:
                rgb = new int[] {255, 0, 0};
                break;
            case WHITE:
                rgb = new int[] {255, 255, 255};
                break;
            case YELLOW:
                rgb = new int[] {225, 220, 50};
                break;
            default:
                rgb = new int[] {0, 0, 0};
                break;
        }
        
        color.setRed(rgb[0]);
        color.setGreen(rgb[1]);
        color.setBlue(rgb[2]);
        return color;
    }
    
    /** Positions in the inventory to place kit selection buttons. */
    private int[] getHotbarIndexes(int items) {
        switch(items) {
            case 9:
                return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            case 8:
                return new int[] {1, 2, 3, 4, 6, 7, 8, 9};
            case 7:
                return new int[] {2, 3, 4, 5, 6, 7, 8};
            case 6:
                return new int[] {2, 3, 4, 6, 7, 8};
            case 5:
                return new int[] {1, 3, 5, 7, 9};
            case 4:
                return new int[] {2, 4, 6, 8};
            case 3:
                return new int[] {3, 5, 7};
            case 2:
                return new int[] {3, 7};
            case 1:
                return new int[] {5};
            default:
                return null;
        }
    }
    
}
