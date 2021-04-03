package rte;

public class DynamicRuntime {

  private static int nextFreeAddress = 0;
  private static int firstFreeAddress = 0;

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
    while (true);
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
    while (true);
  }

  public static boolean isInstance(Object o, SClassDesc dest, boolean asCast) {
    while (true);
  }

  public static SIntfMap isImplementation(
    Object o,
    SIntfDesc dest,
    boolean asCast
  ) {
    while (true);
  }

  public static boolean isArray(
    SArray o,
    int stdType,
    Object unitType,
    int arrDim,
    boolean asCast
  ) {
    while (true);
  }

  public static void checkArrayStore(Object dest, SArray newEntry) {
    while (true);
  }

  public static void nullException() {
    //vielleicht noch was ausgeben...
    while (true);
  }
}
