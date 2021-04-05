package interrupt;

import screen.*;

public class InterruptHandler {

  private static final int MASTER = 0x20, SLAVE = 0xA0;

  public static void initPic() {
    programmChip(MASTER, 0x20, 0x04); //init offset and slave config of master
    programmChip(SLAVE, 0x28, 0x02); //init offset and slave config of slave
    
    final int interruptAdressStart = 0x07E00;
    
    //create IDT
    for(int i = 0; i < 48 ; i++) {
      int currentAdress = interruptAdressStart + i * 4;
      InterruptDescriptor iDescriptor = (InterruptDescriptor) MAGIC.cast2Struct(currentAdress);
      iDescriptor.offset15_0 = (short) 33536; // 1000 0011 0000 000 -> from lecture
      iDescriptor.segmentSelector = (short) 8;  
    }

    loadIDT(interruptAdressStart, 4 * 48); // load IDT

    MAGIC.inline(0xFB); // enable interrupts
  
  }

  private static void programmChip(int port, int offset, int icw3) {
    MAGIC.wIOs8(port++, (byte) 0x11); // ICW1
    MAGIC.wIOs8(port, (byte) offset); // ICW2
    MAGIC.wIOs8(port, (byte) icw3); // ICW3
    MAGIC.wIOs8(port, (byte) 0x01); // ICW4
  }

  private static void loadIDT(int baseAddress, int tableLimit) {
    long tmp=(((long)baseAddress)<<16)|(long)tableLimit;
    MAGIC.inline(0x0F, 0x01, 0x5D); MAGIC.inlineOffset(1, tmp); // lidt [ebp-0x08/tmp]
  }

  @SJC.Interrupt
  public static void nohandle() {
    Screen.printStatic("ici");
    while (true);
  }

  @SJC.Interrupt
  public static void nohandle(int x) {
    Screen.printStatic("ici");
    while (true);
  }
}
