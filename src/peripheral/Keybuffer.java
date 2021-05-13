package peripheral;

public class Keybuffer {

  private static int[] buffer = new int[10];
  public static int cur = 0;

  public static void push(int newInt) {
    buffer[cur] = newInt;
    cur++;
  }

  public static int pop() {
    cur--;
    if (cur < 0) cur = 0;
    int ret = buffer[cur];
    buffer[cur] = Key.NOKEY;
    return ret;
  }

  public static int[] getBuffer() {
    return buffer;
  }
}
