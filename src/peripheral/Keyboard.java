package peripheral;

import helpers.Ringbuffer;
import screen.Screen;

public class Keyboard {
  private static int newElements = 0;
  private static int key = 0;

  public static void processKeyEvent() {
    short scanCode = (short) MAGIC.rIOs8(0x60);

    if (isPressed(scanCode)) {
      Ringbuffer.push(scanCode);
      newElements++;
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
    return Ringbuffer.pop();
  }
}
