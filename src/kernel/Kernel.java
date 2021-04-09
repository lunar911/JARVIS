package kernel;

import interrupt.*;
import rte.*;
import screen.*;

public class Kernel {

  public static void main() {
    Screen screen = new Screen();
    Screen.clearScreen();

    InterruptHandler.initPic();

    BIOS.regs.EAX = 0x0013;
    BIOS.rint(0x10);

    for (int i = 0; i < 320 * 66; i++) MAGIC.wMem32(0xA0000 + i, (int) 0x00);

    for (int i = 320 * 66; i < 320 * 133; i++) MAGIC.wMem32(
      0xA0000 + i,
      (int) 0x0C
    );

    for (int i = 320 * 133; i < 320 * 200; i++) MAGIC.wMem32(
      0xA0000 + i,
      (int) 0x0E
    );

    MAGIC.wIOs8(0x70, (byte) 0);
    byte seconds = (byte) (MAGIC.rIOs8(0x71) & 0xFF);
    byte ts = seconds;
    while(seconds + 20 > ts){
      MAGIC.wIOs8(0x70, (byte) 0);
      ts = (byte) (MAGIC.rIOs8(0x71) & 0xFF);    
    }

    BIOS.regs.EAX = 0x0003;
    BIOS.rint(0x10);

    while(true);
  }
}
