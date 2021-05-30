package applications;
import kernel.
        Task;
import virtualmemory.Page;

public class PageFault extends Task{

    public void run() {
        //int firstpage = 0x0;
        int lastpage = 0xFFFFFFFF - 0xFFF;
        Page P = (Page) MAGIC.cast2Struct(lastpage);
        P.data[0] = 2;

    }

}
