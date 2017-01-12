package me.otisdiver.otisarena.game;

import org.bukkit.Bukkit;

public enum GameState {
    
    // finite state machine - event handlers will run different code depending on which part of the game we're in
    
    // recruiting players - nothing happening, just waiting to hit min. player count
    RECRUITING,
    
    // enough players - giving them 20 (+2) seconds to get ready for the game to start
    PREPARING,
    
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
                setCurrent(PREPARING);
            case PREPARING:
                setCurrent(LOADING);
            case LOADING:
                setCurrent(STARTING);
            case STARTING:
                setCurrent(PLAYING);
            case PLAYING:
                setCurrent(UNLOADING);
            case UNLOADING:
                setCurrent(RECRUITING);
        }
        
        // debug message
        Bukkit.getLogger().info("Game is now " + current);
    }

}
