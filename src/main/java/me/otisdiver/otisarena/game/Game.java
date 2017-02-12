package me.otisdiver.otisarena.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.task.Countdown;
import me.otisdiver.otisarena.task.StartGame;
import me.otisdiver.otisarena.utils.RandUtils;

public class Game {
    
    // Configurables //
    
    // name of the lobby world
    private final String lobbyName = "lobby";
    
    // message sent to players when they join a team
    private final String teamJoinMessage = ChatColor.YELLOW + "You're on the %s%s Team" + ChatColor.YELLOW + "!";
    
    // message sent to players when they get a kit
    private final String kitChosenMessage = ChatColor.YELLOW + "Chose kit: %s" + ChatColor.YELLOW + "!";
    
    // kit name for no kit
    private final String nullKitName = ChatColor.GRAY + "Random";
    
    // game starts 20 seconds after this # of players is met
    private final int minimumPlayers = 2;
    
    // message format for countdown messages
    private final String countdownMessage = ChatColor.DARK_GREEN + " Game starting in " + ChatColor.YELLOW + "%d" + ChatColor.DARK_GREEN + " seconds!";
    
    // Other Class Members // 
    
    private OtisArena main;
    private final Team[]      teams;
    private World             activeWorld;
    
    // a list of all people playing (people conditionally added by events.Join)
    private ArrayList<Player> activePlayers = new ArrayList<Player>();
    
    // track player kits
    private WeakHashMap<Player, Kit> kitChoices = new WeakHashMap<Player, Kit>();
    
    // countdown tasks (before game is started)
    private Countdown interval5;
    private Countdown interval1;
    private StartGame startGame;
    
    // Methods //
    
    /** Game contains all methods necessary to operate the game. */
    public Game(OtisArena main) {
        
        this.main = main;
        
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
    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }
    
    /** Updates the list of players participating in minigames.
     * 
     * @param value the new value of activePlayers */
    public void setActivePlayers(ArrayList<Player> value) {
        activePlayers = value;
    }
    
    /** Starts the countdown for the game to start. */
    public void start() {
        
        // start countdowns (20, 15, 10, 5; 4, 3, 2, 1)
        interval5 = new Countdown(main, 20, 5, countdownMessage);
        interval5.runFuture(20);
        
        interval1 = new Countdown(main, 4, 1, countdownMessage);
        interval1.runFuture(340);
        
        // start the game after the countdown, 22 sec (* 20 ticks/sec = 440) from now
        startGame = new StartGame(main);
        startGame.runFuture(460);
    }
    
    /** Cleanly stops the game and any associated tasks. */
    public void stop() {
        switch(GameState.getCurrent()) {
            case RECRUITING:
                interval5.override();
                interval1.override();
                startGame.cancel();
                break;
            default:
                return;
        }
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
        for(Player player : activePlayers) {
            
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
        for(int i = 0; i < teams.length; i++) {
            teams[i].reset();
        }
    }
    
    /** Returns the team with the highest score, or <code>null</code> for a tie. */
    public Team getWinningTeam() {
        
        // find the team with the highest score
        Team team = null;
        int score = -1;
        
        for(int i = 0; i < teams.length; i++) {
            
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
    
    /** Remembers a player's kit choice.
     * 
     * @param player what player to give the kit to
     * @param kit what Kit to give the player
     */
    public void setKit(Player player, Kit kit) {
        if (player == null) return;
        kitChoices.put(player, kit);
        String kitName = kit == null ? nullKitName : kit.getDisplayName();
        player.sendMessage(String.format(kitChosenMessage, kitName));
    }
    
    /** Retrieves a player's kit choice. Assigns a random kit if none was chosen.
     * 
     * @param player player whose kit is desired
     * @return the player's chosen kit, or random if none
     */
    public Kit getKit(Player player) {
        Kit value = kitChoices.get(player);
        if (value == null) {
            Kit[] kits = Kit.values();
            value = kits[RandUtils.rand(kits.length)];
            setKit(player, value);
        }
        return value;
    }
    
}
