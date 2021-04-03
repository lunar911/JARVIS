package helpers;

public class Math {

  static final char[] DIGITS = {
    '0',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    'A',
    'B',
    'C',
    'D',
    'E',
    'F',
  };

  public static int getDigitCount(int num) {
    return getDigitCount((long) num);
  }

  public static int getHexDigitCount(int num) {
    return getHexDigitCount((long) num);
  }

  public static char Int2HexChar(int num) {
    return Int2HexChar((long) num);
  }

  public static char Int2Ascii(int num) {
    return Int2Ascii((long) num);
  }

  public static int getDigitCount(long num) {
    if(num == 0) return 1;
    
    int count = 0;
    while (num != 0) {
      num /= 10;
      count += 1;
    }
    return count;
  }

  public static int getHexDigitCount(long num) {
    if(num == 0) return 1;
    
    int count = 0;
    while (num != 0) {
      num /= 16;
      count += 1;
    }
    return count;
  }

  public static char Int2HexChar(long num) {
    if (num < 16 && num >= 0) {
      return DIGITS[(int) num];
    } else {
      return '~';
    }
  }

  public static char Int2Ascii(long num) {
    if (num < 10 && num >= 0) {
      return DIGITS[(int) num];
    } else {
      return '~';
    }
  }
}
