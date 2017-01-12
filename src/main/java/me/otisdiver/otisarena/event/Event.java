package me.otisdiver.otisarena.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import me.otisdiver.otisarena.OtisArena;

public abstract class Event implements Listener {
    
    protected OtisArena main;
    
    public Event(OtisArena main) {
        this.main = main;
        
        // self-registration
        Bukkit.getPluginManager().registerEvents(this, main);
    }

}
