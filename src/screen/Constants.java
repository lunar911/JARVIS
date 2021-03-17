package screen;

/*
Farbcode Structure
+----------+-------+-------------+---+---+------+-------------+---+---+
| Bit      | 7     | 6           | 5 | 4 | 3    | 2           | 1 | 0 |
+----------+-------+-------------+---+---+------+-------------+---+---+
| Function | Blink | Background          |Bright| Foreground          |
+----------+-------+-------------+---+---+------+-------------+---+---+
**/

public class Constants {

    public static final byte BLACK = 0x00;
    public static final byte BLUE = 0x01;
    public static final byte GREEN = 0x02;
    public static final byte TURQUOISE = 0x03;
    public static final byte RED = 0x04;
    public static final byte VIOLET = 0x05;
    public static final byte BROWN = 0x06;
    public static final byte GREY = 0x07;
  }