package kernel;

import bomberman.Game;
import interrupt.InterruptHandler;
import interrupt.Mouse;
import virtualmemory.MMU;


public class Kernel {

    public static void main() {
        InterruptHandler.initPic();
        Mouse.mouseInstall();
        MMU mmu = new MMU();
        Game bomberman = new Game();
        while (true) {
            bomberman.GameLoop();
        }
    }
}
