package me.otisdiver.otisarena.task;

import me.otisdiver.otisarena.OtisArena;

public class Countdown extends Task {
    
    private final int duration;
    private final int interval;
    private final int numberOfMessages;
    private final String format;
    private boolean override = false;
    
    public Countdown(OtisArena main, int duration, int interval, String format) {
        super(main);
        this.duration = duration;
        this.interval = interval;
        this.format = format;
        
        // calculate the number of messages that will be sent (math errors here if there's a problem)
        numberOfMessages = duration / interval;
    }
    
    @Override
    public void run() {
        
        // in how many ticks will the countdown end?
        int ticksUntilCompletion = duration * 20;
        
        for(int i = numberOfMessages; i > 0; i--) {
            
            // format message w/ which interval we're counting (e.g. w/ interval length 5: 20, 15, 10, 5)
            int count = i * interval;
            String message = String.format(format, count);
            
            // schedule the message
            // (400 ticks - # of seconds * 20 ticks/second)
            int delay = ticksUntilCompletion - (count * 20);
            new CountMessage(main, this, message).runFuture(delay);
        }
    }
    
    /** Stops the Countdown task and all created CountMessage tasks from running. */
    public void override() {
        
        // cancel the bukkit task
        cancel();
        // tell all the child-tasks not to run anymore
        override = true;
    }
    
    /** Returns true if the countdown should stop. */
    public boolean isOverridden() {
        return override;
    }
}
