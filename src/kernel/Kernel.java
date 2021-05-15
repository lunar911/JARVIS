package kernel;

import interrupt.InterruptHandler;
import screen.Screen;
import terminal.Terminal;


public class Kernel {

  public static void main() {
    InterruptHandler.initPic(); // Do not remove interrupts save lives.

    Scheduler scheduler = new Scheduler();
    scheduler.loop();
  }
}
