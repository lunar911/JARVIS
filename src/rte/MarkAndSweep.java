package rte;

import applications.Objects;
import helpers.Time;
import kernel.Task;
import screen.Screen;

public class MarkAndSweep extends Task {
    private Screen screen;
    private Object marked = null;

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
        Time.wait(200);
    }

    public void mark() {
        Object created_iter = DynamicRuntime.first_O;
        while (created_iter._r_next != null) {
            int iter_adr = MAGIC.cast2Ref(created_iter);
            int firstObjAdr = MAGIC.rMem32(MAGIC.imageBase + 16);

            Object rootSetElement = MAGIC.cast2Obj(firstObjAdr);

            while(rootSetElement._r_next != null) {
                int adr = MAGIC.cast2Ref(rootSetElement);
                if(adr == iter_adr) {

                }
            }
        }
    }

    public void sweep() {

    }
}
