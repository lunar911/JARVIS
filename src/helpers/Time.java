package helpers;

public class Time {
    public static int now = 0;

    public static int getTimestamp() {
        return now;
    }

    public static void wait(int delta) {
        int timestamp = now;
        while(now < timestamp + delta) {
            MAGIC.inline(0x90);
        }
    }
}