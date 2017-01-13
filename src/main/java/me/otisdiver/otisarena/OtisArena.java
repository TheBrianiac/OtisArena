package me.otisdiver.otisarena;

import org.bukkit.plugin.java.JavaPlugin;

import me.otisdiver.otisarena.event.JoinQuit;
import me.otisdiver.otisarena.event.Kill;
import me.otisdiver.otisarena.event.LobbyGuard;
import me.otisdiver.otisarena.event.StartingCanceller;
import me.otisdiver.otisarena.game.Game;

public class OtisArena extends JavaPlugin {
    
    private Game game;
    private JoinQuit joinQuit;
    
    public void onEnable() {
        
        // instantiate the game class
        game = new Game();
        
        // load the config
        ConfigUtils.initiate(this);
        
        // register event listeners
        joinQuit = new JoinQuit(this);
        new StartingCanceller(this);
        new Kill(this);
        new LobbyGuard(this);
    }
    
    public Game getGame() {
        return game;
    }
    
    public void startGameCountdowns() {
        joinQuit.startGameCountdowns();
    }
    
}
