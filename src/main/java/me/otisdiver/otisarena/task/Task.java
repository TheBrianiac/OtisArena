package me.otisdiver.otisarena.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import me.otisdiver.otisarena.OtisArena;

public abstract class Task implements Runnable {

    protected OtisArena main;
    
    private boolean scheduled;
    private BukkitTask task;
    
    public Task (OtisArena main) {
        this.main = main;
    }
    
    /** Runs the task in the main thread after a certain number of ticks.
     * 
     * @param ticks how many ticks in the future to run (approximately 20 ticks per second)
     */
    public void runFuture(int ticks) {
        // prevent a task from being run multiple times
        if (scheduled) return;
        else scheduled = true;
        
        // schedule it
        task = Bukkit.getScheduler().runTaskLater(main, this, ticks);
        
    }
    
    /** Runs the task in the main thread. */
    public void runSync() {
        // prevent a task from being run multiple times
        if (scheduled) return;
        else scheduled = true;
        
        // run it
        task = Bukkit.getScheduler().runTask(main, this);
        
    }
    
    /** Tells Bukkit not to run the task anymore. */
    public void cancel() {
        // prevent a nonexistent task from being run
        if (!scheduled) return;
        else scheduled = false;
        
        // tell bukkit to cancel it
        task.cancel();
        
    }

    public abstract void run();
    
}
