package kernel;

import rte.*;

import interrupt.InterruptHandler;
import screen.Screen;
import peripheral.Keyboard;

public class Kernel {

  public static void main() {
    Screen.clearScreen();
    InterruptHandler.initPic();
    //Screen screen = new Screen();

    /*while(true) {
      if(Keyboard.isNewKeyEvent()) {
        screen.print(Keyboard.getKey());
      }
    }*/
  }
}
