package screen;

import helpers.Math;

public class Screen {

  private static final int VIDEOBUFFER = 2000;

  private byte backgroundColor = Constants.BLACK;
  private Cursor cursor;
  private VidMem vidMem;

  public Screen() {
    vidMem = (VidMem) MAGIC.cast2Struct(0xB8000);

    for(int i = 0; i < VIDEOBUFFER; i++) {
      vidMem.expl[i].ascii = (byte) ' ';
      vidMem.expl[i].color = Constants.GREY;
    }

    cursor = new Cursor();
  }

  public void setColor(int fg, int bg) {
    backgroundColor = (byte) bg;
  }

  public void setCursor(int pos) {
    cursor.setPos(pos);
  }

  public void setCursor(int newX, int newY) {
    cursor.setPos(newX + newY * 80);
  }

  public void setCursor(int newX, int newY, int color) {
    setCursor(newX, newY);
    cursor.setColor(color);
  }

  public int getCursorPos() {
    return cursor.getPos();
  }

  public void printStationary(char c) {
    vidMem.expl[cursor.getPos()].ascii = (byte) c;
    vidMem.expl[cursor.getPos()].color = cursor.getColor();
  }

  public void print(char c) {
    vidMem.expl[cursor.getPos()].ascii = (byte) c;
    vidMem.expl[cursor.getPos()].color = cursor.getColor();
    cursor.moveNextPos();
  }

  public void nextTabStop() {
    cursor.nextTabStop();
  }

  private static int vidPos = 0xB8000;

  public static void printStatic(char c) {
    MAGIC.wMem8(vidPos, (byte) c);
    MAGIC.wMem8(vidPos + 1, (byte) 0x07);
    vidPos += 2;
  }

  public static void printStatic(String s) {
    for (int i = 0; i < s.length(); i++) {
      printStatic(s.charAt(i));
    }
  }

  public void print(int num) {
    long castedValue = (long) num;
    print(castedValue);
  }

  public void printHex(byte b) {
    long castedValue = (long) b;
    printHex(castedValue);
  }

  public void printHex(short s) {
    long castedValue = (long) s;
    printHex(castedValue);
  }

  public void printHex(int num) {
    long castedValue = (long) num;
    printHex(castedValue);
  }

  public void printHex(long num) {
    print("0x");

    if (num < 0) num = num >>> 1;
    if (num != 0) {
      int digitCount = Math.getHexDigitCount(num);

      if (digitCount == 1) {
        print(Math.Int2HexChar(num));
      } else {
        int base = 16;
        cursor.setPos(cursor.getPos() + digitCount - 1); // shift cursor right
        int count = 0;

        while (num > 0) { // print digits right to left
          char digit = Math.Int2HexChar(num % base);

          print(digit); // CAUTION: shifts +1 right
          cursor.setPos(cursor.getPos() - 2); // shift left twice to compensate prev shift
          num /= base;
          count++;
        }
        if (cursor.getPos() == 0) { // workaround for an issue I'm not really getting..
          cursor.setPos(cursor.getPos() + digitCount);
        } else {
          cursor.setPos(cursor.getPos() + digitCount + 1);
        }
      }
    } else {
      print('0');
    }
  }

  public void print(long num) {
    if (num != 0) {
      int base = 10;
      int digitCount = Math.getDigitCount(num);

      if (digitCount == 1) {
        print(Math.Int2Ascii(num));
        num = 0;
      } else {
        cursor.setPos(cursor.getPos() + digitCount - 1); // shift cursor right

        while (num > 0) { // print digits right to left
          char digit = Math.Int2Ascii(num % base);
          print(digit); // CAUTION: shifts +1 right
          cursor.setPos(cursor.getPos() - 2); // shift left twice to compensate prev shift
          num /= base;
        }
        if (cursor.getPos() == 0) { // workaround for an issue I'm not really getting..
          cursor.setPos(cursor.getPos() + digitCount);
        } else {
          cursor.setPos(cursor.getPos() + digitCount + 1);
        }
      }
    } else {
      print('0');
    }
  }

  public void print(String str) {
    for (int i = 0; i < str.length(); i++) print(str.charAt(i));
  }

  public void println() {
    cursor.jumpNextLine();
  }

  public void println(String s) {
    print(s);
    cursor.jumpNextLine();
  }

  public static void clearScreen() {
    VidMem m = (VidMem) MAGIC.cast2Struct(0xB8000);

    for (int i = 0; i < VIDEOBUFFER; i++) {
      m.expl[i].ascii = (byte) ' ';
      m.expl[i].color = (byte) Constants.BLACK;
    }
  }

  public static void blueScreen() {
    VidMem m = (VidMem) MAGIC.cast2Struct(0xB8000);

    for (int i = 0; i < VIDEOBUFFER; i++) {
      m.expl[i].ascii = (byte) ' ';
      m.expl[i].color = (byte) (Constants.BLUE << 4);
    }
  }

  public static void directPrintInt(
    int value,
    int base,
    int x,
    int y,
    int col
  ) {
    Screen screen = new Screen();
    screen.setCursor(x, y, col);

    if (base == 10) screen.print(value); else if (base == 16) screen.printHex(
      value
    );
  }

  public static void directPrintInt(int value, int x, int y, int col) {
    Screen screen = new Screen();
    screen.setCursor(x, y, col);

    screen.print(value);
  }

  public static void directPrintChar(char c, int x, int y, int col) {
    Screen screen = new Screen();
    screen.setCursor(x, y, col);

    screen.print(c);
  }

  public static void directPrintString(String s, int x, int y, int col) {
    Screen screen = new Screen();
    screen.setCursor(x, y, col);
    screen.print(s);
  }
}
