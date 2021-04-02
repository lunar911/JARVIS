package screen;

public class Screen {

  private static final int VIDEOBUFFER = 2000;

  private byte foregroundColor = Constants.GREY;
  private byte backgroundColor = Constants.BLACK;
  private Cursor cursor = new Cursor();
  private VidMem vidMem;

  public Screen() {
    vidMem = (VidMem) MAGIC.cast2Struct(0xB8000);
  }

  public void setColor(int fg, int bg) {
    if (fg <= Constants.GREY && fg >= Constants.BLACK) {
      foregroundColor = (byte) fg;
    }
    if (bg <= Constants.GREY && bg >= Constants.BLACK) {
      backgroundColor = (byte) bg;
    }
  }

  public void setCursor(int newX, int newY) {
    cursor.setX(newX);
    cursor.setY(newY);
  }

  public void print(char c) {
    vidMem.expl[cursor.getPos()].ascii = (byte) c;
    vidMem.expl[cursor.getPos()].color = foregroundColor;
    cursor.moveNextPos();
  }

  public void print(int x) {

  }

  public void printHex(byte b) {}

  public void printHex(short s) {}

  public void printHex(int x) {}

  public void printHex(long x) {}

  public void print(long x) {}

  public void print(String str) {
    for (int i = 0; i < str.length(); i++) print(str.charAt(i));
  }

  public void println() {}

  public static void clearScreen() {
    VidMem m = (VidMem) MAGIC.cast2Struct(0xB8000);

    int i;
    for (i = 0; i < VIDEOBUFFER; i++) {
      m.expl[i].ascii = (byte) ' ';
      m.expl[i].color = (byte) Constants.BLACK;
    }
  }
}
