package applications;

import screen.Screen;

public class PCIScan {

  private static final int PCIAddressPort = 0x0CF8;
  private static final int PCIDataPort = 0x0CFC;

  public static int buildAdress(
    int busNumber,
    int deviceNumber,
    int functionNumber,
    int register
  ) {
    int PCIAddress = 0;
    int ECD = 0x80;
    PCIAddress |= ECD << 24;
    PCIAddress |= busNumber << 16;
    PCIAddress |= functionNumber << 8;
    PCIAddress |= register << 2;
    return PCIAddress;
  }

  public static void printPCIBus(Screen screen) {
    for (int register = 0; register < 64; register++) {
      for (int functionNumber = 0; functionNumber < 8; functionNumber++) {
        for (int deviceNumber = 0; deviceNumber < 32; deviceNumber++) {
          for (int busNumber = 0; busNumber < 256; busNumber++) {
            MAGIC.wIOs32(
              PCIAddressPort,
              buildAdress(busNumber, deviceNumber, functionNumber, register)
            );
            int PCIdata = (int) MAGIC.rIOs32(PCIDataPort);

            if (PCIdata != 0 && PCIdata != ~0) {
              screen.printHex(PCIdata);
              screen.println();
            }
          }
        }
      }
    }
  }
}
