package rte;

public class DynamicRuntime {

    private static int nextFreeAddress = 0;
    private static int firstFreeAddress = 0;

    private static EmptyObject first_eO = null;

    public static void InitEmptyObjects() {
        int imageSize = MAGIC.rMem32(MAGIC.imageBase + 4);
        firstFreeAddress = (MAGIC.imageBase + imageSize + 0xFFF) & ~0xFFF;

        while (StaticMemoryMap.next()) {
            if (StaticMemoryMap.type == 1) {// Memory is free.
                if (StaticMemoryMap.startAddress >= firstFreeAddress) {
                    int eO_address = (int) StaticMemoryMap.startAddress;
                    int eO_size = (int) StaticMemoryMap.size & ~3; // align size to multiple of 4, in doubt decrease size

                    // erase data at empty object area
                    for (int i = eO_address; i < eO_address + eO_size; i++) {
                        MAGIC.wMem8(i, (byte) 0);
                    }

                    Object eO = MAGIC.cast2Obj(eO_address);
                    MAGIC.assign(eO._r_relocEntries, 3);
                    MAGIC.assign(eO._r_type, MAGIC.clssDesc("EmptyObject"));
                    MAGIC.assign(eO._r_scalarSize, eO_size);

                    if (first_eO == null) { // create First empty object
                        first_eO = (EmptyObject) eO;
                        //MAGIC.assign(eO._r_next, null); Assign _r_next on second object
                    } else {
                        if(first_eO.nextFreeAddress == null) { // Second empty object
                            first_eO.nextFreeAddress = (EmptyObject) eO;
                            MAGIC.assign(eO._r_next, eO);
                        }
                        // Any other empty object
                        EmptyObject iter = first_eO;
                        // get last EmptyObject
                        while(iter.nextFreeAddress != null) {
                            iter = iter.nextFreeAddress;
                        }
                        MAGIC.assign(iter._r_next, eO);
                    }
                }
            }
        }
    }

    public static Object newInstance(
            int scalarSize,
            int relocEntries,
            SClassDesc type
    ) {
        if (nextFreeAddress == 0) {
            int imageSize = MAGIC.rMem32(MAGIC.imageBase + 4);
            firstFreeAddress = (MAGIC.imageBase + imageSize + 0xFFF) & ~0xFFF;
            nextFreeAddress = firstFreeAddress;
        }

        int rs = relocEntries << 2;
        scalarSize = (scalarSize + 3) & ~3; // Align to multiples of 4
        int start = nextFreeAddress; // Start of current new object
        nextFreeAddress += rs + scalarSize; // offset for next new object

        for (int i = start; i < nextFreeAddress; i += 4) {
            MAGIC.wMem32(i, 0); // init memory for object with 0
        }

        Object newObj = MAGIC.cast2Obj(start + rs); // offset object by its references
        MAGIC.assign(newObj._r_relocEntries, relocEntries);
        MAGIC.assign(newObj._r_scalarSize, scalarSize);
        MAGIC.assign(newObj._r_type, type);
        MAGIC.assign(newObj._r_next, MAGIC.cast2Obj(nextFreeAddress));

        return newObj;
    }

    public static SArray newArray(
            int length,
            int arrDim,
            int entrySize,
            int stdType,
            Object unitType
    ) {
        while (true) ;
    }

    public static void newMultArray(
            SArray[] parent,
            int curLevel,
            int destLevel,
            int length,
            int arrDim,
            int entrySize,
            int stdType,
            Object unitType
    ) {
        while (true) ;
    }

    public static boolean isInstance(Object o, SClassDesc dest, boolean asCast) {
        while (true) ;
    }

    public static SIntfMap isImplementation(
            Object o,
            SIntfDesc dest,
            boolean asCast
    ) {
        while (true) ;
    }

    public static boolean isArray(
            SArray o,
            int stdType,
            Object unitType,
            int arrDim,
            boolean asCast
    ) {
        while (true) ;
    }

    public static void checkArrayStore(Object dest, SArray newEntry) {
        while (true) ;
    }

    public static void nullException() {
        //vielleicht noch was ausgeben...
        while (true) ;
    }
}
