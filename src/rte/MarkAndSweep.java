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
        mark(true);
        screen.println("Sweeping Objects...");
        sweep();
        screen.println("Sweeping Objects...");

        mark(false);
        Objects.printObjectCounts(screen);
    }

    public void mark(boolean mark) {
        Object createdIter = DynamicRuntime.first_O;
        while (createdIter._r_next != null) {
            createdIter.mark(mark);
            createdIter = createdIter._r_next;
        }
    }

    static int i = 0;
    public void sweep() {
        Object current = DynamicRuntime.last_O_rootset;
        Object next = current._r_next;

        while (current._r_next != null) {
            next = current._r_next;
            if (current.mark) {
                i++;
                DynamicRuntime.deleteObject(current);
            }

            current = next;

        }
    }
}
