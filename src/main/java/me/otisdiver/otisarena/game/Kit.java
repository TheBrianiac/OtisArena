package me.otisdiver.otisarena.game;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.otisdiver.otisarena.task.kit.Ability;
import me.otisdiver.otisarena.task.kit.Firespit;
import me.otisdiver.otisarena.task.kit.Groundslam;
import me.otisdiver.otisarena.task.kit.Rollup;

public enum Kit {
    
    GORILLA(Groundslam.class, ChatColor.DARK_GREEN + "Gorilla", "Groundslam", "Send everyone near you flying away (7 second cooldown)."),
    
    ARMADILLO(Rollup.class, ChatColor.DARK_GRAY + "Armadillo", "Rollup", "Block incoming and outgoing damage for 3 seconds."),
    
    SALAMANDER(Firespit.class, ChatColor.RED + "Salmander", "Firespit", "Shoot an explosive fireball (3 second cooldown).");
    
    public static final Material buttonDefault = Material.IRON_HELMET;
    public static final Material buttonChosen = Material.GOLD_HELMET;
    public static final Material abilityTool = Material.STONE_SWORD;
    
    private Class<? extends Ability> ability;
    private String displayName;
    private String abilityName;
    private String abilityDesc;
    
    Kit (Class<? extends Ability> ability, String displayName, String abilityName, String abilityDesc) {
        this.ability = ability;
        this.displayName = displayName;
        this.abilityName = abilityName;
        this.abilityDesc = abilityDesc;
    }
    
    public Class<? extends Ability> getAbility() {
        return ability;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getAbilityName() {
        return abilityName;
    }
    
    public String getAbilityDesc() {
        return abilityDesc;
    }
    
    public ItemStack getNewButton() {
        // create item
        ItemStack button = new ItemStack(Material.IRON_HELMET);
        
        // set the name/lore
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(displayName);
        
        ArrayList<String> lore = new ArrayList<String>();
        StringBuilder nameLine = new StringBuilder();
        nameLine.append(ChatColor.YELLOW).append("Ability: ").append(abilityName);
        lore.add(nameLine.toString());
        StringBuilder descLine = new StringBuilder();
        descLine.append(ChatColor.GRAY).append(abilityDesc);
        lore.add(descLine.toString());
        meta.setLore(lore);
        
        // finish
        button.setItemMeta(meta);
        return button;
    }

}
