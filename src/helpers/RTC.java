package helpers;

public class RTC {
    public static int getCurrentHour() {
        MAGIC.wIOs8(0x70, (byte)0x04);
        return (int)MAGIC.rIOs8(0x71)&0xFF;
    }

    public static int getCurrentMinute() {
        MAGIC.wIOs8(0x70, (byte) 0x02);
        return (int)MAGIC.rIOs8(0x71)&0xFF;
    }

    public static int getCurrentSecond() {
        MAGIC.wIOs8(0x70, (byte)0x00);
        return (int)MAGIC.rIOs8(0x71)&0xFF;
    }
}
