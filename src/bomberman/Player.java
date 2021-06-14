package bomberman;

public class Player {

    public int pos;
    private final Grid grid;
    private boolean settingBomb = false;
    private static final int startPos = 17;

    public Player(Grid grid) {
        pos = startPos;
        this.grid = grid;
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

    public void setBomb() {
        this.settingBomb = true;
        grid.setBomb(getPos());
    }
}