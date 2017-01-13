package me.otisdiver.otisarena.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Game {
    
    // Configurables //
    
    // name of the lobby world
    private final String lobbyName = "lobby";
    
    // message sent to players when they join a team
    private final String teamJoinMessage = ChatColor.YELLOW + "You're on the %s%s Team" + ChatColor.YELLOW + "!";
    
    // game starts 20 seconds after this # of players is met
    private final int minimumPlayers = 2;
    
    // Other Class Members // 
    
    private final Team[] teams;
    private World activeWorld;
    // a list of all people playing (people conditionally added by events.Join)
    private ArrayList<Player> activePlayers = new ArrayList<Player>();
    
    // Methods //
    
    /** Game contains all methods necessary to operate the game. */
    public Game() {
        
        // create the teams
        teams = new Team[] {
            new Team("Red", ChatColor.RED),
            new Team("Blue", ChatColor.BLUE)
        };
        
    }
    
    /** Returns the minimum number of players needed to start a game. */
    public int getMinimumPlayers() {
        return minimumPlayers;
    }
    
    /** Gets the list of players participating in minigames.
     * 
     * @return list of active (participating) players */
    public List<Player> getActivePlayers() {
        return activePlayers;
    }
    
    /** Updates the list of players participating in minigames.
     * 
     * @param value the new value of activePlayers */
    public void setActivePlayers(ArrayList<Player> value) {
        activePlayers = value;
    }
    
    /** Get a team object with a given team chatcolor.
     * 
     * @param teamColor the color of the desired team
     * @return a Team object with the given color value
     * @throws InvalidTeamException if no team exists with the desired color, this error is thrown */
    public Team getTeam(ChatColor teamColor) throws InvalidTeamException {
        
        // iterate through all of the teams in the array
        for(int i = 0; i < teams.length; i++) {
            
            // if the given color is equal to this team's color, return that team
            if (teamColor == teams[i].getColor()) return teams[i];
            
        }
        
        // no team found? error!
        throw new InvalidTeamException();
        
    }
    
    /** Assigns a player to a team with a given color.
     * 
     * @param player the player to assign the team to
     * @param teamColor the desired team color */
    public void assignTeamColor(Player player, ChatColor teamColor) {
        
        // find the team with the given color
        Team team;
        try {
            team = getTeam(teamColor);
        }
        // if an invalid color was given (code error), print an error and stop
        catch (InvalidTeamException e) {
            e.printStackTrace();
            return;
        }
        
        // add the player to the team
        team.addPlayer(player);
        
    }
    
    /** Evenly distributes online players across available teams. */
    public void distributePlayersTeams(Collection<? extends Player> players) {
        
        // randomize
        Collections.shuffle(activePlayers);
        
        // iterate through all participating players
        Team team;
        String message;
        int index = 0;
        
        int loops = 0;
        // iterate through all the players on the list of active players
        for (Player player : activePlayers) {
            
            // results in alternating values of 0 or 1 (or higher values if >2 teams are configured) 
            index = loops % teams.length;
            
            // add the player to the team and tell them
            team = teams[index];
            team.addPlayer(player);
            
            message = String.format(teamJoinMessage, team.getColor(), team.getName());
            player.sendMessage(message);
            
            // fin
            loops++;
        }
        
    }
    
    /** Returns the team a player is assigned to. */
    public Team getPlayerTeam(Player player) {
        
        // loop through the members of each team
        for(int i = 0; i < teams.length; i++) {
            Team team = teams[i];
            for(Player teamMember : team.getPlayers()) {
                // return the player's team
                if (teamMember == player) {
                    return team;
                }
            }
        }
        
        // couldn't find the player on any of the teams, return null
        return null;
        
    }
    
    /** Removes all players and points from the teams. */
    public void resetTeams() {
        
        // reset all teams
        for (int i = 0; i < teams.length; i++) {
            teams[i].reset();
        }
    }
    
    /** Returns the team with the highest score, or <code>null</code> for a tie. */
    public Team getWinningTeam() {
        
        // find the team with the highest score
        Team team = null;
        int score = -1;
        
        for (int i = 0; i < teams.length; i++) {
            
            Team forTeam = teams[i];
            int forScore = forTeam.getScore();
            
            // definite victory:
            if (forScore > score) {
                team = forTeam;
                score = forScore;
            }
            // tie:
            else if (forScore == score) {
                team = null; // (null value will be removed if another team is a higher score than the tie)
                score = forScore;
            }
        }
        
        return team;
    }
    
    /** Adds a point to the given player's team. */
    public void playerScore(Player player) {
        
        // add a score to the player's team
        getPlayerTeam(player).addScore();
    }
    
    /** Returns the name of the lobby world. */
    public String getLobby() {
        return lobbyName;
    }

    public World getActiveWorld() {
        return activeWorld;
    }

    public void setActiveWorld(World activeWorld) {
        this.activeWorld = activeWorld;
    }
    
}
