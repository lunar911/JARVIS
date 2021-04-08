package interrupt;

import screen.*;

public class InterruptHandler {

  private static final int MASTER = 0x20, SLAVE = 0xA0;

  public static void initPic() {
    final int interruptAdressStart = 0x07E00;

    //create IDT
    for (int i = 0; i < 48; i++) {
      int currentAdress = interruptAdressStart + i * 8;
      InterruptDescriptor iDescriptor = (InterruptDescriptor) MAGIC.cast2Struct(
        currentAdress
      );
      int methodOffset = MAGIC.mthdOff("InterruptHandler", "nohandle");
      int handlerMemAddress = MAGIC.cast2Ref(MAGIC.clssDesc("InterruptHandler")) + methodOffset;
      int handlerRefAddress = MAGIC.rMem32(handlerMemAddress);
      int handlerReference = handlerRefAddress + MAGIC.getCodeOff();

      iDescriptor.leftOffset = (short) ((handlerReference & 0xFFFF0000) >>> 16); 
      iDescriptor.segmentSelector = (short) 8;
      iDescriptor.configurationBits = (short) 0x8E00;
      iDescriptor.rightOffset = (short) (handlerReference & 0xFFFF);
    }

    loadIDT(interruptAdressStart, 8 * 48); // load IDT

    programmChip(MASTER, 0x20, 0x04); //init offset and slave config of master
    programmChip(SLAVE, 0x28, 0x02); //init offset and slave config of slave
    MAGIC.inline(0xFB); // enable ALL interrupts
  }

  private static void programmChip(int port, int offset, int icw3) {
    MAGIC.wIOs8(port++, (byte) 0x11); // ICW1
    MAGIC.wIOs8(port, (byte) offset); // ICW2
    MAGIC.wIOs8(port, (byte) icw3); // ICW3
    MAGIC.wIOs8(port, (byte) 0x01); // ICW4
  }

  private static void loadIDT(int baseAddress, int tableLimit) {
    long tmp = (((long) baseAddress) << 16) | (long) tableLimit;
    MAGIC.inline(0x0F, 0x01, 0x5D);
    MAGIC.inlineOffset(1, tmp); // lidt [ebp-0x08/tmp]
  }

  @SJC.Interrupt
  public static void nohandle() {
    Screen.printStatic("ici");
    while (true);
  }
}
