package kernel;

import bios.BIOS;
import bomberman.Game;
import helpers.Time;
import interrupt.InterruptHandler;
import virtualmemory.MMU;


public class Kernel {

    public static void main() {
        InterruptHandler.initPic();
        MMU mmu = new MMU();
        Game bomberman = new Game();
        while (true) {
            bomberman.GameLoop();
        }
    }
}
