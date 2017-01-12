package me.otisdiver.otisarena.task;

import me.otisdiver.otisarena.OtisArena;

public class CountMessage extends FutureBroadcast {

    Countdown parent;
    
    public CountMessage(OtisArena main, Countdown parent, String message) {
        super(main, message);
        this.parent = parent;
    }
    
    @Override
    public void run() {
        
        // if the count-down hasn't been cancelled, run the code (from FutureBroadcast)
        if (!parent.isOverridden()) {
            super.run();
        }
    }

}
