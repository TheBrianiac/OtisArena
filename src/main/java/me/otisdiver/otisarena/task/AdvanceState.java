package me.otisdiver.otisarena.task;

import me.otisdiver.otisarena.OtisArena;
import me.otisdiver.otisarena.game.GameState;

public class AdvanceState extends Task {

    /** Calls GameState:advance */
    public AdvanceState(OtisArena main) {
        super(main);
    }

    public void run() {
        GameState.advance();
    }
}
