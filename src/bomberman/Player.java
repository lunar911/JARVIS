package bomberman;

public class Player {

    public int health;
    public int pos;

    public Player() {
        health = 3;
        pos = 0;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
