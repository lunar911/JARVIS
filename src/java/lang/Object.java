package java.lang;

import peripheral.StaticV24;
import rte.DynamicRuntime;
import rte.SClassDesc;

public class Object {
    public final SClassDesc _r_type = null;
    public final Object _r_next = null;
    public final int _r_relocEntries = 0, _r_scalarSize = 0;
    public final boolean mark = false;

    public void mark(boolean mark) {
        DynamicRuntime.ObjectCount++;
        MAGIC.assign(this.mark, mark);
        int thisAddress = MAGIC.cast2Ref(this);
        for (int i = 3; i <= _r_relocEntries; i++) {
            int relocAddress = thisAddress - i * MAGIC.ptrSize;
            Object tmp = MAGIC.cast2Obj(MAGIC.rMem32(relocAddress));

            if(tmp == null) return;

            if (tmp.mark == mark) {
                ;
            } else {
                tmp.mark(mark);
            }
        }
    }
}
