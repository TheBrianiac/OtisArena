package me.otisdiver.otisarena;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import me.otisdiver.otisarena.event.ClickHandler;
import me.otisdiver.otisarena.event.GameDeath;
import me.otisdiver.otisarena.event.JoinQuit;
import me.otisdiver.otisarena.event.LobbyGuard;
import me.otisdiver.otisarena.event.StartingCanceller;
import me.otisdiver.otisarena.event.WorldGuard;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.task.EndGame;
import me.otisdiver.otisarena.task.kit.Ability;
import me.otisdiver.otisarena.utils.ConfigUtils;

public class OtisArena extends JavaPlugin {
    
    private Game game;
    private Random randomSet;
    
    public void onEnable() {
        
        // instantiate the game class
        game = new Game(this);
        
        // load various static classes
        ConfigUtils.initiate(this);
        Ability.initiate(this);
        
        // register event listeners
        new JoinQuit(this);
        new StartingCanceller(this);
        new GameDeath(this);
        new LobbyGuard(this);
        new WorldGuard(this);
        new ClickHandler(this);
        
        // create a set of random numbers
        randomSet = new Random();
    }
    
    public void onDisable() {
        
        // run end game routines
        new EndGame(this, false).run();
    }
    
    public Game getGame() {
        return game;
    }
    
}
