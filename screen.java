public class VidChar extends STRUCT {

  public byte ascii, color;
}

public class VidMem extends STRUCT {

  @SJC(offset = 0, count = 2000)
  public VidChar[] expl;

  @SJC(offset = 0, count = 2000)
  public short[] chars;
}

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

public class Screen {

  public static final int VIDEOBUFFER = 2000;

  public static void clearScreen() {
    VidMem m = (VidMem) MAGIC.cast2Struct(0xB8000);

    int i;
    for (i = 0; i < VIDEOBUFFER; i++) {
      m.expl[i].ascii = (byte) ' ';
      m.expl[i].color = (byte) Constants.BLACK;
    }
  }
}
