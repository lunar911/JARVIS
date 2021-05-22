package rte;

import applications.Objects;
import helpers.Time;
import kernel.Task;
import peripheral.StaticV24;
import screen.Screen;

public class MarkAndSweep extends Task {
    private final Screen screen;

    public MarkAndSweep(Screen screen) {
        this.screen = screen;
        screen.setCursor(0);
    }

    public void run() {
        Objects.printObjectCounts(screen);
        screen.println("Marking Objects...");
        mark();
        screen.println("Sweeping Objects...");
        sweep();
        Objects.printObjectCounts(screen);
        Time.wait(20);
    }

    public void mark() {
        Object createdIter = DynamicRuntime.first_O;
        while (createdIter._r_next != null) {
            if(reachableByRootset(createdIter)) {
                break;
            } else {
                StaticV24.println("Marked object.");
                MAGIC.assign(createdIter.delete, true);
            }
            createdIter = createdIter._r_next;
        }
    }

    public void sweep() {
        Object current = DynamicRuntime.first_O;
        while(current._r_next != null) {
            if(current.delete) {
                DynamicRuntime.deleteObject(current);
            }
            StaticV24.println(MAGIC.addr(current));
            current = current._r_next;
        }
    }

    public boolean reachableByRootset(Object obj) {
        boolean ret = false;
        int firstObjAdr = MAGIC.rMem32(MAGIC.imageBase + 16);
        Object rootSetElement = MAGIC.cast2Obj(firstObjAdr);

        while (rootSetElement._r_next != null) {
            int adr = MAGIC.cast2Ref(rootSetElement);
            int createdAddr = MAGIC.cast2Ref(obj);
            if(adr == createdAddr) {
                ret = true;
                break;
            }
            rootSetElement = rootSetElement._r_next;
        }
        return ret;
    }
}
