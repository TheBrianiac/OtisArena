package me.otisdiver.otisarena.task;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import me.otisdiver.otisarena.ConfigUtils;
import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.Game;
import me.otisdiver.otisarena.game.InventoryBuilder;

public class Respawn extends Task {
    
    private final Player player;
    private final Game game;
    
    public Respawn(OtisArena main, Player player) {
        super(main);
        this.game = main.getGame();
        this.player = player;
    }

    @Override
    public void run() {
        
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(ConfigUtils.getRandomSpawn(game.getActiveWorld()));
        player.setHealth(20.0);
        player.setFoodLevel(20);
        new InventoryBuilder(main, player);
        
    }
    
}
