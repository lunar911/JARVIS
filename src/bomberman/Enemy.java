package bomberman;


public class Enemy {
    private int pos = 0;
    private boolean alive = true;

    public Enemy() {

    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setAlive(boolean alive) {this.alive = alive;}
    public boolean isAlive() { return alive;}

    public void move(Grid grid) {
        int dir = RNG.getRandomInt(230, 3);

        switch (dir) {
            case 0:
                if (grid.isWalkable(pos - 16)) {
                    grid.resetChunk(pos);
                    grid.setEnemy(pos - 16);
                    setPos(pos - 16);
                }
                break;
            case 1:
                if (grid.isWalkable(pos + 16)) {
                    grid.resetChunk(pos);
                    grid.setEnemy(pos + 16);
                    setPos(pos + 16);
                }
                break;
            case 2:
                if (grid.isWalkable(pos - 1)) {
                    grid.resetChunk(pos);
                    grid.setEnemy(pos - 1);
                    setPos(pos - 1);
                }
                break;
            case 3:
                if (grid.isWalkable(pos + 1)) {
                    grid.resetChunk(pos);
                    grid.setEnemy(pos + 1);
                    setPos(pos + 1);
                }
                break;
            default:
                return;
        }
    }

}
