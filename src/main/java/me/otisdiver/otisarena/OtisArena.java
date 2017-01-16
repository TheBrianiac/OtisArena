package me.otisdiver.otisarena;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.otisdiver.otisarena.event.JoinQuit;
import me.otisdiver.otisarena.event.GameDeath;
import me.otisdiver.otisarena.event.LobbyGuard;
import me.otisdiver.otisarena.event.StartingCanceller;
import me.otisdiver.otisarena.event.WorldGuard;
import me.otisdiver.otisarena.game.Game;

public class OtisArena extends JavaPlugin {
    
    private Game game;
    
    public void onEnable() {
        
        // instantiate the game class
        game = new Game(this);
        
        // load the config
        ConfigUtils.initiate(this);
        
        // register event listeners
        new JoinQuit(this);
        new StartingCanceller(this);
        new GameDeath(this);
        new LobbyGuard(this);
        new WorldGuard(this);
    }
    
    public void onDisable() {
        
        // unload world
        Bukkit.unloadWorld(game.getActiveWorld().getName(), false);
    }
    
    public Game getGame() {
        return game;
    }
    
}
