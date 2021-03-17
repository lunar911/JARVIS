package helpers;

public class Math {

  /*
    1278 : 16 = 79 remainder: 14 (= E) (Nr:1278-(79*16)=14)
    79 : 16 =  4 remainder: 15 (= F) (Nr:79-(4*16)=15)
    4 : 16 =  0 remainder:  4       (Nr:4-(0*16)=4)
    dec(1278) == hex(4FE)
    **/

  public static String Int2Hexstring(int i) {
    final int base = 16;
  }

  private static char int2HexChar(int i) {
    char ret = ' '; 
    switch (i) {
      case 0:
        ret = '0';
        break;
      case 1:
        ret = '1';
        break;
      case 2:
        ret = '2';
        break;
      case 3:
        ret = '3';
        break;
      case 4:
        ret = '4';
        break;
      case 5:
        ret = '5';
        break;
      case 6:
        ret = '6';
        break;
      case 7:
        ret = '7';
        break;
      case 8:
        ret = '8';
        break;
      case 9:
        ret = '9';
        break;
      case 10:
        ret = 'A';
        break;
      case 11:
        ret = 'B';
        break;
      case 12:
        ret = 'C';
        break;
      case 13:
        ret = 'D';
        break;
      case 14:
        ret = 'E';
        break;
      case 15:
        ret = 'F';
        break;
      default:
        ret = ' ';
        break;
    }
    return ret;
  }
}
