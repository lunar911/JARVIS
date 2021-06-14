package bomberman;

import bios.BIOS;
import helpers.Time;
import peripheral.Key;
import peripheral.StaticV24;

public class Game {
    private final Grid grid;
    private final KeyController keyController;
    private final Player player;
    private static final int looptime = 3; // ~ 0,5 second

    public Game() {
        BIOS.enterGraphicmode();
        grid = new Grid();
        grid.setPlayer(0);
        keyController = new KeyController();
        player = new Player();

    }

    public void GameLoop() {
        // react on player input
        int input = keyController.getPlayerKey();
        StaticV24.println(input);
        switch (input) {
            case Key.RIGHT_ARROW:
                grid.movePlayerRight(player);
                break;
            case Key.LEFT_ARROW:
                grid.movePlayerLeft(player);
                break;
            case Key.UP_ARROW:
                grid.movePlayerUp(player);
                break;
            case Key.DOWN_ARROW:
                grid.movePlayerDown(player);
                break;
        }

        Time.wait(looptime);
    }
}
