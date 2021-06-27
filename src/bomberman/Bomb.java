package bomberman;


public class Bomb {
    private static final int explodingTimer = 5;
    private int timer = 0;
    private int pos = 0;
    private boolean active = false;
    private boolean exploding = false;

    Bomb() {
    }

    public void setBomb(int pos) {
        active = true;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void tick(Grid grid) {
        if (active) {
            timer++;
            if (timer > explodingTimer) {
                explode(grid);
            }
            if (timer > explodingTimer + 1) {
                cleanUpExplosion(grid);
            }
        }
    }

    public void explode(Grid grid) {
        grid.setExplosionCenter(pos);
        grid.setExplosionHorizontal(pos + 1);
        grid.setExplosionHorizontal(pos - 1);
        grid.setExplosionOrthogonal(pos - 16);
        grid.setExplosionOrthogonal(pos + 16);
        exploding = true;
    }

    public void cleanUpExplosion(Grid grid) {
        grid.resetChunk(pos);
        grid.resetChunk(pos + 1);
        grid.resetChunk(pos - 1);
        grid.resetChunk(pos - 16);
        grid.resetChunk(pos + 16);
        exploding = false;

        active = false;
        timer = 0;
    }
}
