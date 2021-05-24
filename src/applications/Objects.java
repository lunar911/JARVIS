package applications;

import peripheral.StaticV24;
import screen.Screen;
import rte.DynamicRuntime;

public class Objects {
    public static void printObjectCounts(Screen screen) {
        screen.print("Static Objects: ");
        screen.print(countStaticObjects());
        screen.print(" Created Objects: ");
        screen.print(DynamicRuntime.countObjects());
        screen.print(" EmptyObjects: ");
        screen.print(DynamicRuntime.countEmptyObjects());
        screen.println();
    }

    public static int countStaticObjects() {
        int firstObjAdr = MAGIC.rMem32(MAGIC.imageBase + 16);

        Object first = MAGIC.cast2Obj(firstObjAdr);
        int count = 0;
        while(first._r_next != null) {
            first = first._r_next;
            count++;
        }
        return count;
    }
}
