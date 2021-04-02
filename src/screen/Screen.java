package screen;

import helpers.*;

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
    if (x != 0) {
      int base = 10;
      int digitCount = Math.getDigitCount(x);
      cursor.setPos(cursor.getPos() + digitCount - 1); // shift cursor right

      int count = 0;
      while (x > 0) { // print digits right to left
        char digit = Math.Int2Ascii(x % base);
        print(digit); // CAUTION: shifts +1 right
        cursor.setPos(cursor.getPos() - 2); // shift left twice to compensate prev shift
        x /= base;
        count++;
      }
      if (cursor.getPos() == 0) {
        cursor.setPos(cursor.getPos() + digitCount);
      } else {
        cursor.setPos(cursor.getPos() + digitCount + 1);
      }
    } else {
      print('0');
    }
  }

  public void printHex(byte b) {}

  public void printHex(short s) {}

  public void printHex(int num) {
    setColor(Constants.VIOLET, Constants.BLACK);
    print("0x");
    int base = 16;
    int digitCount = Math.getHexDigitCount(num); // include 0x-Prefix
    cursor.setPos(cursor.getPos() + digitCount - 1); // shift cursor right
    int count = 0;

    while (num > 0) { // print digits right to left
      char digit = Math.Int2HexChar(num % base);

      print(digit); // CAUTION: shifts +1 right
      cursor.setPos(cursor.getPos() - 2); // shift left twice to compensate prev shift
      num /= base;
      count++;
    }
    cursor.setPos(cursor.getPos() + digitCount + 1);
    setColor(Constants.GREY, Constants.BLACK);
  }

  public void printHex(long x) {}

  public void print(long x) {}

  public void print(String str) {
    for (int i = 0; i < str.length(); i++) print(str.charAt(i));
  }

  public void println() {}

  public static void clearScreen() {
    VidMem m = (VidMem) MAGIC.cast2Struct(0xB8000);

    for (int i = 0; i < VIDEOBUFFER; i++) {
      m.expl[i].ascii = (byte) ' ';
      m.expl[i].color = (byte) Constants.BLACK;
    }
  }
}
