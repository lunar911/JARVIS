package kernel;

import interrupt.InterruptHandler;
import peripheral.StaticV24;
import rte.DynamicRuntime;
import virtualmemory.MMU;


public class Kernel {

    public static void main() {
        InterruptHandler.initPic(); // Do not remove interrupts save lives.
        MMU mmu = new MMU();

        Scheduler scheduler = new Scheduler();
        scheduler.loop();
    }
}
