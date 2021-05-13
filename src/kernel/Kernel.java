package kernel;

import interrupt.InterruptHandler;
import screen.Screen;
import terminal.Terminal;


public class Kernel {

  public static void main() {
    InterruptHandler.initPic(); // Do not remove interrupts save lives.

    Terminal term = new Terminal();
    Screen screen = term.getScreen();

    term.run();
  }
}
