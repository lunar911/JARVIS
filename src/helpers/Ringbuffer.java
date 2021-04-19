package helpers;

public class Ringbuffer {

  private static int[] buffer = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  public static int cur = 0;
  public static final int size = 10;


  public static void push(int newInt) {
    buffer[cur] = newInt;
    cur++;
  }

  public static int pop() {
    int ret = buffer[cur];
    cur--;
    if (cur < 0) cur = 0;
    return ret;
  }

  public static int[] getBuffer() {
    return buffer;
  }
}
