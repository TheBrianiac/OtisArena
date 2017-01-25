package me.otisdiver.otisarena.task;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.game.GameState;
import me.otisdiver.otisarena.game.InventoryBuilder;
import me.otisdiver.otisarena.game.Team;
import me.otisdiver.otisarena.utils.ConfigUtils;

public class StartGame extends Task {
    
    private final String countdownMessage = ChatColor.AQUA + " Game starting in " + ChatColor.YELLOW + "%s seconds!";
    
    private final int gameLengthTicks = 360 * 20; // average 20 ticks/second
    
    private final int warningSeconds = 30;
    
    private final String warningMessage = " " + ChatColor.RED + warningSeconds + " seconds remaining!";

    private final String worldName;
    
    private final Game game;
    
    public StartGame(OtisArena main) {
        
        super(main);
        
        game = main.getGame();
        
        worldName = ConfigUtils.getRandomWorld();
    }
    
    @Override
    public void run() {
        
        // go to next state - LOADING
        GameState.advance();
        
        // assign teams
        game.distributePlayersTeams(game.getActivePlayers());
        
        // load the (random) arena
        World arena = Bukkit.createWorld(new WorldCreator(worldName));
        game.setActiveWorld(arena);
        
        // send players into game world
        HashMap<Location, Team> usedLocations = new HashMap<Location, Team>();
        
        for (Player player : game.getActivePlayers()) {
            
            Team playerTeam = game.getPlayerTeam(player);
            
            boolean spawned = false;
            while (!spawned) {
                Location spawn = ConfigUtils.getRandomSpawn(arena);
                Team spawnTeam = usedLocations.get(spawn);
                
                // if the spawn belongs to that player's team or no team, teleport to it
                if (spawnTeam == null || spawnTeam.equals(playerTeam)) {
                    player.teleport(spawn);
                    usedLocations.put(spawn, game.getPlayerTeam(player));
                    spawned = true;
                }
            }
            
            // create their inventory
            new InventoryBuilder(main, player);
        }
        
        // unload the lobby, don't save changes
        Bukkit.unloadWorld(game.getLobby(), false);
        
        // run a countdown (after 1 second)
        new Countdown(main, 10, 1, countdownMessage).runFuture(10);
        
        // advance state after countdown (after 11 seconds)
        new AdvanceState(main).runFuture(221);
        
        // schedule warning <warningSeconds> seconds before game ends
        new FutureBroadcast(main, warningMessage).runFuture(gameLengthTicks - (warningSeconds * 20));
        
        // end game after gameLengthTicks
        new EndGame(main, true).runFuture(gameLengthTicks);
        
        // go to next state - STARTING
        GameState.advance();
        
    }

}
