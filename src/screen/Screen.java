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

        int count = 0;
        while (num > 0) { // print digits right to left
          char digit = Math.Int2Ascii(num % base);
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

  public void print(String str) {
    for (int i = 0; i < str.length(); i++) print(str.charAt(i));
  }

  public void println() {
    cursor.jumpNextLine();
  }

  public static void clearScreen() {
    VidMem m = (VidMem) MAGIC.cast2Struct(0xB8000);

    for (int i = 0; i < VIDEOBUFFER; i++) {
      m.expl[i].ascii = (byte) ' ';
      m.expl[i].color = (byte) Constants.BLACK;
    }
  }
}
