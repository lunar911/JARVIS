package peripheral;

import helpers.Ringbuffer;
import screen.Screen;

public class Keyboard {

  private static Ringbuffer ringbuffer;
  private static int newElements = 0;
  private static boolean shift = false;
  private static boolean caps = false;
  private static boolean alt = false;
  private static int key = 0;
  private static int e0_code = 0;
  private static short e1_code = 0;

  public static void processKeyEvent() {
    short scanCode = (short) MAGIC.rIOs8(0x60);

    if (isPressed(scanCode)) {
      key = scanCode;
      newElements++;
    }
  }
  
  public static boolean isPressed(int scanCode) {
    return (scanCode & 0x80) == 0;
  }

  public static boolean isPrintable(int key) {
    if (key < 127 && key > 0) {
      return true;
    } else {
      return false;
    }
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
    return key;
  }
}
