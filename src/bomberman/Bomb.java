package bomberman;


public class Bomb {
    private static final int explodingTimer = 5;
    private int timer = 0;
    private int pos = 0;
    private boolean active = false;

    Bomb() {
    }

    public void setBomb(int pos) {
        active = true;
        this.pos = pos;
    }

    public boolean isActive() {
        return active;
    }

    public void tick(Grid grid) {
        if (active) {
            timer++;
            if (timer > explodingTimer) {
                explode(grid);

            }
        }
    }

    public void resetTimer() {
        timer = 0;
    }

    public void explode(Grid grid) {
        grid.setExplosionCenter(pos);
        grid.setExplosionHorizontal(pos + 1);
        grid.setExplosionHorizontal(pos - 1);
        grid.setExplosionOrthogonal(pos - 16);
        grid.setExplosionOrthogonal(pos + 16);

        active = false;
        timer = 0;
    }
}