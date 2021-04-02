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
    if(num == 0) return 1;
    
    int count = 0;
    while (num != 0) {
      num /= 10;
      count += 1;
    }
    return count;
  }

  public static int getHexDigitCount(int num) {
    if(num == 0) return 1;
    
    int count = 0;
    while (num != 0) {
      num /= 16;
      count += 1;
    }
    return count;
  }

  public static char Int2HexChar(int num) {
    if (num < 16 && num >= 0) {
      return DIGITS[num];
    } else {
      return '~';
    }
  }

  public static char Int2Ascii(int num) {
    if (num < 10 && num >= 0) {
      return DIGITS[num];
    } else {
      return '~';
    }
  }
}
