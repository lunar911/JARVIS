package applications;

import bios.BIOS;
import screen.Screen;

public class MemLayout {

  public static void printMemLayout(Screen screen) {
    int storeAt = 0x8000;

    screen.print("Base Address       | Length             | Type");
    screen.println();

    BIOS.regs.EBX = 0;
    while (true) {
      BIOS.regs.EAX = 0x0000E820;
      BIOS.regs.EDX = 0x534D4150; // SMAP

      // store result in
      BIOS.regs.EDI = storeAt;
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

      long baseAddress = MAGIC.rMem64(storeAt);
      long length = MAGIC.rMem64(storeAt + 8);
      int type = MAGIC.rMem32(storeAt + 16);
      int acpi = MAGIC.rMem32(storeAt + 20);

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

      screen.print(", ACPI = ");
      screen.print(acpi);
      screen.println();
    }
  }
}
