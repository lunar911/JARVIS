package bomberman;

import bios.BIOS;
import helpers.Time;
import peripheral.Key;


public class Game {
    private final Grid grid;
    private final KeyController keyController;
    private final Player player;
    private final Enemy[] enemies;
    private static final int looptime = 3;
    private static final int playerSafeArea = 51;


    public Game() {
        BIOS.enterGraphicmode();
        grid = new Grid();
        keyController = new KeyController();
        player = new Player(grid);
        enemies = new Enemy[3];

        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy();
        }

        spawnEnemies();
    }

    public void GameLoop() {
        processPlayerInput();
        bombTick();
        enemyMove(grid);

        boolean allDead = false;

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                allDead = false;
                break;
            } else {
                allDead = true;
            }
        }

        if(allDead) win();

        Time.wait(looptime);
    }

    public void win() {
        grid.win();
    }

    public void lose() {
        grid.lose();
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

    private void bombTick() {
        for (int i = 0; i < player.bombs.length; i++) {
            player.bombs[i].tick(grid);
            if (player.bombs[i].isExploding()) {
                int bombpos = player.bombs[i].getPos();

                // check if player hit himself
                if (player.getPos() == bombpos ||
                        player.getPos() == bombpos - 1 ||
                        player.getPos() == bombpos + 1 ||
                        player.getPos() == bombpos + 16 ||
                        player.getPos() == bombpos - 16) {
                    lose();
                }

                // check if bomb hit enemy
                for (Enemy enemy : enemies) {
                    if (enemy.isAlive()) {
                        if (enemy.getPos() == bombpos||
                                enemy.getPos() == bombpos - 1 ||
                                enemy.getPos() == bombpos + 1 ||
                                enemy.getPos() == bombpos + 16 ||
                                enemy.getPos() == bombpos - 16) {
                            enemy.setAlive(false);
                        }
                    }
                }
            }
        }
    }

    private void spawnEnemies() {
        for (Enemy enemy : enemies) {
            int randpos = RNG.getRandomInt(100, 160);

            while (!grid.isWalkable(randpos) || randpos < playerSafeArea) {
                randpos = RNG.getRandomInt(100, 160);
            }

            grid.setEnemy(randpos);
            enemy.setPos(randpos);
        }
    }

    private void enemyMove(Grid grid) {
        if (RNG.getRandomInt(4021, 4) % 4 == 0) {
            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    enemy.move(grid);
                    if (enemy.getPos() == player.getPos()) {
                        lose();
                    }
                }
            }
        }
    }
}
