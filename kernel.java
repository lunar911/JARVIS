package kernel;

public class Kernel {
  private static int vidMem = 0xB8000;

  public static void main() {
    Screen.clear();
    Screen.print((char)'a', (byte) 0x02);
    while (true);
  }
}
