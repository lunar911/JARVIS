package applications;

import screen.Screen;

public class PCIScan {

  private static final int PCIAddressPort = 0x0CF8;
  private static final int PCIDataPort = 0x0CFC;

  public static int buildAddress(int busNumber, int deviceNumber) {
    int PCIAddress = 0;
    int ECD = 0x80;
    PCIAddress |= ECD << 24;
    PCIAddress |= busNumber << 16;
    PCIAddress |= deviceNumber << 11;
    return PCIAddress;
  }

  public static String printDeviceBaseClass(int baseClassCode) {
    String s = "";

    switch (baseClassCode) {
      case 0x00:
        s = "old Device";
        break;
      case 0x01:
        s = "Massstorage";
        break;
      case 0x02:
        s = "Network-Controller";
        break;
      case 0x03:
        s = "Display-Controller";
        break;
      case 0x04:
        s = "Multimedia-Device";
        break;
      case 0x05:
        s = "Memory-Controller";
        break;
      case 0x06:
        s = "Bridge";
        break;
      case 0x07:
        s = "Communication-Controller";
        break;
      case 0x08:
        s = "System-Peripheral";
        break;
      case 0x09:
        s = "input-Device";
        break;
      case 0x0A:
        s = "Docking-Station";
        break;
      case 0x0B:
        s = "Processor-Unit";
        break;
      case 0x0C:
        s = "serial-bus";
        break;
      case 0x0D:
        s = "Wireless Communication Device";
        break;
      case 0x0E:
        s = "intelligent controller";
        break;
      case 0x0F:
        s = "Satellite-Communication";
        break;
      default:
        s = "weird something";
    }
    return s;
  }

  public static void printDevice(
    Screen screen,
    int currentAddress,
    int PCIdata
  ) {
    int deviceID = PCIdata >> 16;
    screen.print(deviceID);
    screen.nextTabStop();
    screen.print("|");

    int vendorID = PCIdata & 0xFFFF;
    screen.print(vendorID);
    screen.nextTabStop();
    screen.print("|");

    currentAddress |= 2 << 2; // get second register
    MAGIC.wIOs32(PCIAddressPort, currentAddress);
    PCIdata = MAGIC.rIOs32(PCIDataPort);

    int baseClassCode = PCIdata >>> 24;
    screen.print(baseClassCode);
    screen.nextTabStop();
    screen.print("|");

    int subClassCode = (PCIdata >>> 16) & 0xFF;
    screen.print(subClassCode);
    screen.nextTabStop();
    screen.print("|");

    screen.print(printDeviceBaseClass(baseClassCode));
    screen.println();
  }

  public static void scanPCIBus(Screen screen) {
    screen.print("deviceID | vendorID | baseCCode | subCCode");
    screen.println();

    for (int deviceNumber = 0; deviceNumber < 32; deviceNumber++) {
      for (int busNumber = 0; busNumber < 256; busNumber++) {
        int currentAddress = buildAddress(busNumber, deviceNumber);
        MAGIC.wIOs32(PCIAddressPort, currentAddress);
        int PCIdata = MAGIC.rIOs32(PCIDataPort);

        if (PCIdata != 0 && PCIdata != ~0) {
          printDevice(screen, currentAddress, PCIdata);
        }
      }
    }
  }
}
