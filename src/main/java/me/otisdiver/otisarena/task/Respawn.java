package me.otisdiver.otisarena.task;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.game.GameState;
import me.otisdiver.otisarena.game.InventoryBuilder;
import me.otisdiver.otisarena.utils.ConfigUtils;

public class Respawn extends Task {
    
    private final Player player;
    private final Game   game;
    
    public Respawn(OtisArena main, Player player) {
        super(main);
        this.game = main.getGame();
        this.player = player;
    }
    
    @Override
    public void run() {
        // if a game's not underway, stop
        if (!GameState.getCurrent().equals(GameState.PLAYING)) return;
        
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(ConfigUtils.getRandomSpawn(game.getActiveWorld()));
        player.setHealth(20.0);
        player.setFoodLevel(20);
        new InventoryBuilder(main, player);
        
    }
    
}
