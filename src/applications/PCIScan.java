package applications;

import screen.Screen;

public class PCIScan {

  private static final int PCIAddressPort = 0x0CF8;
  private static final int PCIDataPort = 0x0CFC;

  public static int buildAddress(int busNumber, int deviceNumber, int functionNumber, int register) {
    int PCIAddress = 0;
    int ECD = 0x80;
    PCIAddress |= ECD << 24;
    PCIAddress |= busNumber << 16;
    PCIAddress |= functionNumber << 8;
    PCIAddress |= register << 2;
    return PCIAddress;
  }

  public static void printDevice(Screen screen, int busNumber, int deviceNumber, int functionNumber, int register, int PCIdata) {
    screen.print(busNumber);
    screen.nextTabStop();
    screen.print("|");
    screen.print(deviceNumber);
    screen.nextTabStop();
    screen.print("|");
    screen.print(functionNumber);
    screen.nextTabStop();
    screen.print("|");
    screen.print(register);
    screen.nextTabStop();
    screen.print("|");

    int deviceID = PCIdata >> 16;
    screen.print(deviceID);
    screen.nextTabStop();
    screen.print("|");
    
    int vendorID = PCIdata & 0xFFFF;
    screen.print(vendorID);
    screen.nextTabStop();
    screen.print("|");
    
    int baseClassCode = PCIdata >> 24;
    screen.print(baseClassCode);
    screen.nextTabStop();
    screen.print("|");
    
    int subClassCode = PCIdata >> 16 & 0xFF;
    screen.print(subClassCode);
    screen.println();
  }


  public static void scanPCIBus(Screen screen) {
    int lastfound = 0;
    screen.print("busN | deviceN | functionN | register | deviceID | vendorID | baseCCode | subCCode");
    screen.println();

    for (int register = 0; register < 64; register++) {
      for (int functionNumber = 0; functionNumber < 8; functionNumber++) {
        for (int deviceNumber = 0; deviceNumber < 32; deviceNumber++) {
          for (int busNumber = 0; busNumber < 256; busNumber++) {
            int currentAddress = buildAddress(busNumber, deviceNumber, functionNumber, register);
            MAGIC.wIOs32(PCIAddressPort, currentAddress);
            int PCIdata = (int) MAGIC.rIOs32(PCIDataPort);

            if (PCIdata != 0 && PCIdata != ~0 && PCIdata != lastfound) {
              lastfound = PCIdata;
              printDevice(screen, busNumber, deviceNumber, functionNumber, register, PCIdata);
            }
          }
        }
      }
    }
  }
}
