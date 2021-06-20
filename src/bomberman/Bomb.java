package bomberman;

import peripheral.StaticV24;

public class Bomb {
    public static final int explodingTimer = 5;
    public int timer = 0;
    private final Grid grid;
    public int pos;

    Bomb(Grid grid, int pos) {
        this.grid = grid;
        this.pos = pos;
    }

    public void tick() {
        timer++;
        if (timer > explodingTimer) {
            explode();
        }
    }

    public void resetTimer() {
        timer = 0;
    }

    public void explode() {
        StaticV24.println("BOOM!");
    }
}
