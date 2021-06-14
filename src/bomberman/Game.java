package bomberman;

import bios.BIOS;
import helpers.Time;
import peripheral.Key;


public class Game {
    private final Grid grid;
    private final KeyController keyController;
    private final Player player;
    private static final int looptime = 3; // ~ 0,5 second

    public Game() {
        BIOS.enterGraphicmode();
        grid = new Grid();
        keyController = new KeyController();
        player = new Player(grid);
    }

    public void GameLoop() {
        processPlayerInput();

        Time.wait(looptime);
    }

    public void processPlayerInput() {
        int input = keyController.getPlayerKey();
        switch (input) {
            case Key.RIGHT_ARROW:
                player.moveRight();
                break;
            case Key.LEFT_ARROW:
                player.moveLeft();
                break;
            case Key.UP_ARROW:
                player.moveUp();
                break;
            case Key.DOWN_ARROW:
                player.moveDown();
                break;
            case Key.SPACE:
                player.setBomb();
                break;
        }
    }
}
