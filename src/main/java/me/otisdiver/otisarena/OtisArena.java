package me.otisdiver.otisarena;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.otisdiver.otisarena.event.ClickHandler;
import me.otisdiver.otisarena.event.GameDeath;
import me.otisdiver.otisarena.event.JoinQuit;
import me.otisdiver.otisarena.event.LobbyGuard;
import me.otisdiver.otisarena.event.StartingCanceller;
import me.otisdiver.otisarena.event.WorldGuard;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.task.kit.Ability;
import me.otisdiver.otisarena.utils.ConfigUtils;

public class OtisArena extends JavaPlugin {
    
    private Game game;
    
    @Override
    public void onEnable() {
        
        // instantiate the game class
        game = new Game(this);
        
        // register commands
        Bukkit.getPluginCommand("game").setExecutor(new Commander(this));
        
        // load various static classes
        ConfigUtils.init(this);
        Ability.init(this);
        
        // register event listeners
        new JoinQuit(this);
        new StartingCanceller(this);
        new GameDeath(this);
        new LobbyGuard(this);
        new WorldGuard(this);
        new ClickHandler(this);
    }
    
    public Game getGame() {
        return game;
    }
    
}
