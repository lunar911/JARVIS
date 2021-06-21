package bomberman;

import peripheral.StaticV24;

public class Player {

    public int pos;
    private final Grid grid;
    private boolean settingBomb = false;
    private static final int startPos = 17;
    public final Bomb[] bombs = new Bomb[3];

    public Player(Grid grid) {
        this.grid = grid;
        pos = startPos;

        for (int i = 0; i < bombs.length; i++) {
            bombs[i] = new Bomb();
        }

        grid.setPlayer(startPos);
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
        grid.setPlayer(pos);
    }

    public void movePlayer(int pos) {
        if (pos < 0 || pos > 159) return;
        if (!grid.isWalkable(pos)) return;

        int oldPos = getPos();
        setPos(pos);

        if (!settingBomb) {
            grid.resetChunk(oldPos); // don't overwrite bomb
        } else {
            settingBomb = false;
        }
    }

    public void moveRight() {
        int newPos = getPos() + 1;
        movePlayer(newPos);
    }

    public void moveLeft() {
        int newPos = getPos() - 1;
        movePlayer(newPos);
    }

    public void moveUp() {
        int newPos = getPos() - 16;
        movePlayer(newPos);
    }

    public void moveDown() {
        int newPos = getPos() + 16;
        movePlayer(newPos);
    }

    public boolean hasInactiveBomb() {
        for (Bomb bomb : bombs) {
            if (!bomb.isActive()) return true;
        }
        return false;
    }

    public void setBomb() {
        if (hasInactiveBomb()) {
            this.settingBomb = true;
            for (Bomb bomb : bombs) {
                if (!bomb.isActive()) {
                    bomb.setBomb(getPos());
                    grid.setBomb(getPos());
                    return;
                }
            }
        }
    }
}
