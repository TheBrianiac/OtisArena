package me.otisdiver.otisarena.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.ConfigUtils;
import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.game.GameState;

public class StartGame extends Task {
    
    private final String countdownMessage = ChatColor.AQUA + "Game starting in " + ChatColor.YELLOW + "%s seconds!";
    
    private final int gameLengthTicks = 360 * 20; // average 20 ticks/second
    
    private final int warningSeconds = 30;
    
    private final String warningMessage = "" + ChatColor.RED + warningSeconds + " seconds remaining!";

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
        
        // load the (random) arena
        World arena = Bukkit.createWorld(new WorldCreator(worldName));
        
        // send players into game world
        for (Player player : game.getActivePlayers()) {
            
            Location spawn = ConfigUtils.getRandomSpawn(arena);
            player.teleport(spawn);
            
        }
        
        // unload the lobby, don't save changes
        Bukkit.unloadWorld(game.getLobby(), false);
        
        // run a countdown (with a brief delay for thinking)
        new Countdown(main, 10, 1, countdownMessage).runFuture(10);
        
        // schedule warning <warningSeconds> seconds before game ends
        new FutureBroadcast(main, warningMessage).runFuture(gameLengthTicks - (warningSeconds * 20));
        
        // end game after gameLengthTicks
        new EndGame(main).runFuture(gameLengthTicks);
        
        // go to next state - STARTING
        GameState.advance();
        
    }

}
