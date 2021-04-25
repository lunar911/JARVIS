package peripheral;

public class Keyboard {

  private static int newElements = 0;
  private static int e0_code = 0;
  private static int e1_code = 0;
  private static int e1_prev = 0;

  private static boolean shift = false;
  private static boolean caps = false;
  private static boolean alt = false;

  public static void processKeyEvent() {
    short scanCode = (short) MAGIC.rIOs8(0x60);
    int break_code = 0;

    if (
      ((scanCode & 0x80) == 0) &&
      ((e1_code != 0) || (scanCode != 0xE1)) &&
      ((e0_code != 0) || (scanCode != 0xE0))
    ) {
      break_code = 1;
      scanCode &= ~0x80;
    }

    if (e0_code == 1) {
      if ((scanCode == 0x2a) || (scanCode == 0x36)) {
        e0_code = 0;
        return;
      }
      storeToBuffer(scanCode);
      e0_code = 0;
    } else if (e1_code == 2) {
      // Fertiger e1-Scancode
      // Zweiten Scancode in hoeherwertiges Byte packen
      e1_prev |= ((short) scanCode << 8);
      storeToBuffer(scanCode);
      e1_code = 0;
    } else if (e1_code == 1) {
      e1_prev = scanCode;
      e1_code++;
    } else if (scanCode == 0xE0) {
      // Anfang eines e0-Codes
      e0_code = 1;
    } else if (scanCode == 0xE1) {
      // Anfang eines e1-Codes
      e1_code = 1;
    } else {
      // normaler Scancode
      storeToBuffer(scanCode);
    }
  }

  public static void storeToBuffer(int scanCode) {
    checkSpecialKeys(scanCode);

    if (isPressed(scanCode)) {
      int key = Layout.translatePhysToLogicalKey(scanCode, shift, caps, alt);
      Keybuffer.push(key);
      newElements++;
    }
  }

  public static void checkSpecialKeys(int scanCode) {
    if (scanCode == Key.LEFT_SHIFT || scanCode == Key.RIGHT_SHIFT) {
      if (shift) {
        shift = false;
      } else {
        shift = true;
      }
    }

    if (scanCode == Key.CAPSLOCK) {
      if (caps) {
        caps = false;
      } else {
        caps = true;
      }
    }

    if (
      scanCode == Key.ALT_GR ||
      scanCode == Key.LEFT_ALT ||
      scanCode == Key.RIGHT_ALT
    ) {
      if (alt) {
        alt = false;
      } else {
        alt = true;
      }
    }
  }

  public static boolean isPressed(int scanCode) {
    return (scanCode & 0x80) == 0;
  }

  public static boolean isNewKeyEvent() {
    if (newElements == 0) {
      return false;
    } else {
      newElements--;
      return true;
    }
  }

  public static int getKey() {
    return Keybuffer.pop();
  }
}
