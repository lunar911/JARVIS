package java.lang;

import rte.SClassDesc;

public class Object {
  public final SClassDesc _r_type = null;
  public final Object _r_next = null;
  public final int _r_relocEntries = 0, _r_scalarSize = 0;
}

package java.lang;

public class String {
  private char[] value;
  private int count;

  @SJC.Inline
  public int length() {
    return count;
  }

  @SJC.Inline
  public char charAt(int i) {
    return value[i];
  }
}

package rte;

public class SArray {
  public final int length = 0, _r_dim = 0, _r_stdType = 0;
  public final Object _r_unitType = null;
}

package rte;

public class SClassDesc {
  public SClassDesc parent;
  public SIntfMap implementations;
}

package rte;

public class SIntfDesc {
}

package rte;

public class SIntfMap {
  public SIntfDesc owner;
  public SIntfMap next;
}

package rte;

public class SMthdBlock {
}

package rte;

public class DynamicRuntime {
  private static int nextFreeAddress = 0;

  public static Object newInstance(int scalarSize, int relocEntries, SClassDesc type) {
    if(nextFreeAddress == 0)
    {
      nextFreeAddress = (MAGIC.imageBase+MAGIC.rMem32(MAGIC.imageBase+4)+0xFFF) &~0xFFF;
    }
    
    int start, rs, i; // temporäre Variablen
    rs = relocEntries << 2; // pro Reloc werden 4 Bytes benötigt
    scalarSize = (scalarSize + 3) & ~3; // Alignierung der Skalare
    start = nextFreeAddress; // Start des Objektes
    nextFreeAddress += rs + scalarSize; // nächstes Objekt hinter aktuellem platzieren
    
    for (i = start; i < nextFreeAddress; i += 4)
    {
      MAGIC.wMem32(i, 0); // 0-Init
    }

    Object me = MAGIC.cast2Obj(start + rs); // Objekt platzieren
    MAGIC.assign(me._r_relocEntries, relocEntries); // Zahl der Relocs in Objekt eintragen
    MAGIC.assign(me._r_scalarSize, scalarSize); // Größe der Skalare in Objekt eintragen
    MAGIC.assign(me._r_type, type); // Typ des Objekts in Objekt eintragen
    return me; // Objekt an Aufrufer zurückgeben
  }

  public static SArray newArray(int length, int arrDim, int entrySize, int stdType, Object unitType) {
    while (true)
      ;
  }

  public static void newMultArray(SArray[] parent, int curLevel, int destLevel, int length, int arrDim, int entrySize,
      int stdType, Object unitType) {
    while (true)
      ;
  }

  public static boolean isInstance(Object o, SClassDesc dest, boolean asCast) {
    while (true)
      ;
  }

  public static SIntfMap isImplementation(Object o, SIntfDesc dest, boolean asCast) {
    while (true)
      ;
  }

  public static boolean isArray(SArray o, int stdType, Object unitType, int arrDim, boolean asCast) {
    while (true)
      ;
  }

  public static void checkArrayStore(Object dest, SArray newEntry) {
    while (true)
      ;
  }
}
