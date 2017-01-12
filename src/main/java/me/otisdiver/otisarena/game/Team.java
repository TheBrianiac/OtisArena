package me.otisdiver.otisarena.game;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Team {
    
    private String name;
    
    private ChatColor color;
    
    private Collection<Player> players;
    
    private int score = 0;
    
    public Team (String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }
    
    /** Get the team's name, e.g. "Red." */
    public String getName() {
        return name;
    }
    
    /** Get the team's color, e.g. ChatColor.RED */
    public ChatColor getColor() {
        return color;
    }
    
    public Collection<Player> getPlayers() {
        return players;
    }
    
    public int getScore() {
        return score;
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    public void removePlayer(Player player) {
        players.add(player);
    }
    
    /** Increment the team's score. */
    public void addScore() {
        score = score++;
    }
    
    /** Removes all players and resets the score. */
    public void reset() {
        players = null;
        score = 0;
    }

}
