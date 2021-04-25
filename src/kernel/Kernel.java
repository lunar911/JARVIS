package kernel;

import applications.MemLayout;
import interrupt.InterruptHandler;
import peripheral.Key;
import peripheral.Keyboard;
import rte.*;
import screen.Screen;


public class Kernel {

  public static void main() {
    Screen.clearScreen();
    InterruptHandler.initPic();
    Screen screen = new Screen();

    MemLayout.printMemLayout(screen);
    screen.println();


    while (true) {
      if (Keyboard.isNewKeyEvent()) {
        int key = Keyboard.getKey();
        if (isPrintable(key)) screen.print((char) key);
      }
    }
  }

  public static boolean isPrintable(int key) {
    if (key > 31 && key < 127) {
      return true;
    } else {
      return false;
    }
  }
}
