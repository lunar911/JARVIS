package kernel;

import applications.*;
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

    MemoryMap.printMemLayout(screen);
    screen.println();

    PCIScan.scanPCIBus(screen);
    screen.println();

    while (true) {
      if (Keyboard.isNewKeyEvent()) {
        int key = Keyboard.getKey();
        if (isPrintable(key)) screen.print((char) key);
        if (key == Key.ESCAPE) MAGIC.inline(0xCC);
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
