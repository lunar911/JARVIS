package kernel;

import interrupt.InterruptHandler;
import peripheral.StaticV24;
import rte.DynamicRuntime;


public class Kernel {

    public static void main() {
        InterruptHandler.initPic(); // Do not remove interrupts save lives.
        Scheduler scheduler = new Scheduler();
        scheduler.loop();
    }
}
