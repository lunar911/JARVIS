package applications;
import kernel.
        Task;
public class PageFault extends Task{

    public void run() {
        int addr = 0xFFFFFFFF - 100;/*0x0+;*/
        Object o = MAGIC.cast2Obj(addr);

        o = o._r_next;
    }
}
