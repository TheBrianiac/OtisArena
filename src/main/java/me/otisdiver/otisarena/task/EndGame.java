package me.otisdiver.otisarena.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.game.GameState;
import me.otisdiver.otisarena.game.Team;

public class EndGame extends Task {

    private final String winMessage = "%s" + ChatColor.BOLD + "%s Team" + ChatColor.GRAY + " won the game!";
    private final String tieMessage = ChatColor.GRAY + "The game ended in a " + ChatColor.WHITE + "tie!";
    
    private Game game;
    
    public EndGame(OtisArena main) {
        super(main);
        this.game = main.getGame();
    }

    @Override
    public void run() {
        
        // go to next state - UNLOADING
        GameState.advance();
        
        // load the lobby
        World lobby = Bukkit.createWorld(new WorldCreator(game.getLobby()));
        
        // return players to lobby
        for (Player player : game.getActivePlayers()) {
            
            player.teleport(lobby.getSpawnLocation());
            player.setGameMode(GameMode.SURVIVAL);
        }
        
        // unload arena, don't save changes
        Bukkit.unloadWorld(game.getActiveWorld(), false);
        
        // find and announce winner (after a brief delay for thinking)
        Team winner = game.getWinningTeam();
        if (winner != null) {
            new FutureBroadcast(main, String.format(winMessage, winner.getColor(), winner.getName())).runFuture(20);
        }
        else {
            new FutureBroadcast(main, tieMessage).runFuture(20);
        }
        
        // start over (return to state RECRUITING)
        game.resetTeams();
        GameState.advance();
        
    }

}
