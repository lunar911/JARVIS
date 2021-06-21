package bomberman;

import helpers.RTC;

public class RNG {
    public static int randomSeed = 0;

    public static int getRandomInt(int seed, int max) {
        randomSeed += RTC.getCurrentHour() + RTC.getCurrentMinute() + RTC.getCurrentSecond();
        randomSeed = randomSeed + seed * 1103515245 + 12345;
        int ret = (int) (randomSeed / 65536) % (max+1);
        if(ret < 0) ret *= -1;
        return ret;
    }
}
