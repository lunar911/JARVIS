package kernel;

import interrupt.InterruptHandler;
import peripheral.Keyboard;
import rte.*;
import screen.Screen;

public class Kernel {

  public static void main() {
    Screen.clearScreen();
    InterruptHandler.initPic();
    Screen screen = new Screen();

    while (true) {
      if (Keyboard.isNewKeyEvent()) {
        screen.print(Keyboard.getKey());
        screen.print(' ');
      }
    }
  }
}
