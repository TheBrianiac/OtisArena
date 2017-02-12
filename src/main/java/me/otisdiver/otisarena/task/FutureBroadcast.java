package me.otisdiver.otisarena.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.otisdiver.otisarena.OtisArena;

public class FutureBroadcast extends Task {
    
    // the character that will precede hex color codes (e.g. &e for yellow)
    private final char hexMarker = '&';
    
    private String message;
    
    /** Runnable chat message, for setting messages to run with {@code BukkitScheduler}.
     * 
     * @param message the chat message, with hex codes preceded by &
     */
    public FutureBroadcast(OtisArena main, String message) {
        super(main);
        
        // format message
        message = ChatColor.translateAlternateColorCodes(hexMarker, message);
        this.message = message;
    }
    
    @Override
    public void run() {
        // send the saved message
        Bukkit.broadcastMessage(message);
    }
    
}
