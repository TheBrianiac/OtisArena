package me.otisdiver.otisarena.game;

import org.bukkit.Bukkit;

public enum GameState {
    
    // finite state machine - event handlers will run different code depending on which part of the game we're in
    
    // recruiting players - nothing happening, just waiting to hit min. player count
    RECRUITING,
    
    // loading the world, teleporting players in
    LOADING,
    
    // counting down - until gameplay starts
    STARTING,
    
    // gameplay - waiting for victory conditions to be met
    PLAYING,
    
    // unloading the world, returning to lobby
    UNLOADING;
    
    // single (static) variable of what state the game is in
    private static GameState current = RECRUITING;
    
    /* Returns the current game state. */
    public static GameState getCurrent() {
        return current;
    }
    
    /* Manually sets the current game state. */
    public static void setCurrent(GameState state) {
        
        if (state == null) return;
        current = state;
    }
    
    /* Moves the current game state to the next state. */
    public static void advance() {
        switch (current) {
            // each case runs setCurrent for the next value in the finite state machine
            case RECRUITING:
                setCurrent(LOADING);
            break;
            case LOADING:
                setCurrent(STARTING);
            break;
            case STARTING:
                setCurrent(PLAYING);
            break;
            case PLAYING:
                setCurrent(UNLOADING);
            break;
            case UNLOADING:
                setCurrent(RECRUITING);
            break;
        }
        
        // debug message
        Bukkit.getLogger().info("Game is now " + current);
    }

}
