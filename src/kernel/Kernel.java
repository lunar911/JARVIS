package kernel;

import screen.*;
import interrupt.*;

public class Kernel {

  public static void main() {
    Screen.clearScreen();

    InterruptHandler.initPic();
    
    MAGIC.inline(0xCD, 0x00); // trigger divideError interrupt

    while (true);
  }
}
