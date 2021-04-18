package peripheral;

import helpers.Ringbuffer;
import screen.Screen;

public class Keyboard {

  private static Ringbuffer ringbuffer;
  private static int newElements = 0;
  private static boolean shift = false;
  private static boolean caps = false;
  private static boolean alt = false;

  public static void processKeyEvent() {
    short newScanCode = (short) (MAGIC.rIOs8(0x60) & 0xFF);
    newElements++;
    ringbuffer.push(0x30);

    newScanCode &= 0x7F;
    if (newScanCode <= 0xDF) { // 1-Byte Key
      newElements++;
      int key = 0x30;
      ringbuffer.push(key);
    } else if (newScanCode == 0xE0) {} else if (newScanCode == 0xE1) {} else {} // 2-Byte Key // 3-Byte Key // ignore
  }

  public static boolean isPrintable(int key) {
    if (key < 127 && key > 0) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isNewKeyEvent() {
    return true;
  }

  public static int getKey() {
    return ringbuffer.pop();
  }
}
