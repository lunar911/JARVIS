package applications;

import bios.BIOS;
import screen.Screen;

public class MemLayout {
  private final static int buffer = 0x8000;

  public static void printMemLayout(Screen screen) {

    screen.print("Base Address       | Length             | Type");
    screen.println();

    BIOS.regs.EBX = 0;
    while (true) {
      BIOS.regs.EAX = 0x0000E820;
      BIOS.regs.EDX = 0x534D4150; // SMAP

      // store result in
      BIOS.regs.EDI = buffer;
      // buffer size >= 20 bytes
      BIOS.regs.ECX = 24;

      BIOS.rint(0x15);

      // is done?
      if (BIOS.regs.EAX != 0x534D4150 || BIOS.regs.EBX == 0) break;

      // error?
      if ((BIOS.regs.FLAGS & BIOS.F_CARRY) != 0) {
        screen.print("Error: Carry flag was set");
        break;
      }

      long baseAddress = MAGIC.rMem64(buffer);
      long length = MAGIC.rMem64(buffer + 8);
      int type = MAGIC.rMem32(buffer + 16);

      screen.printHex(baseAddress);

      screen.print(" | ");
      screen.printHex(length);

      screen.print(" | (");
      screen.print(type);
      screen.print(") ");
      switch (type) {
        case 1:
          screen.print("Free Memory");
          break;
        case 2:
          screen.print("Reserved Memory");
          break;
        case 3:
          screen.print("ACPI reclaimable");
          break;
        case 4:
          screen.print("ACPI NVS memory");
          break;
        case 5:
          screen.print("Area containing bad memory");
          break;
        default:
          screen.print("Unknown");
      }

      screen.println();
    }
  }
}
