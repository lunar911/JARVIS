package helpers;

public class Ringbuffer {

  private static int[] buffer = new int[10];
  public static int cur = 0;
  private final int size = 10;

  public void push(int newInt) {
    buffer[cur] = newInt;
    cur++;
    if (cur == size) {
      cur = 0;
    }
  }

  public int pop() {
    int ret = buffer[cur];
    cur--;
    if (cur < 0) cur = size - 1;
    return ret;
  }

  public int[] getBuffer() {
    return buffer;
  }

  public int getTop() {
    int tmp = cur - 1;
    if (tmp < 0) {
      return buffer[size - 1];
    } else {
      return buffer[tmp];
    }
  }
}
