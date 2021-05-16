package rte;

import screen.Screen;
import bios.BIOS;

public class DynamicRuntime {

    public static Object first_O = null;
    private static Object last_O = null;
    private static EmptyObject first_eO = null;

    public static void InitEmptyObjects() {
        BIOS.regs.EBX = 0;

        int imageSize = MAGIC.rMem32(MAGIC.imageBase + 4);
        int firstFreeAddress = (MAGIC.imageBase + imageSize + 0xFFF) & ~0xFFF;

        while (StaticMemoryMap.next()) {
            if (StaticMemoryMap.type == 1) {// Memory is free.
                if (StaticMemoryMap.startAddress + StaticMemoryMap.size >= firstFreeAddress) {
                    int eO_address, eO_size;
                    if (StaticMemoryMap.startAddress >= firstFreeAddress) { // Take memory segment from BIOS as is.
                        eO_address = (int) StaticMemoryMap.startAddress;
                        eO_size = (int) StaticMemoryMap.size;
                    } else { // Shrink memory segment
                        eO_address = (int) firstFreeAddress;
                        eO_size = (int) StaticMemoryMap.size - (firstFreeAddress - eO_address);
                    }

                    // erase data at empty object area
                    for (int i = eO_address; i < eO_address + eO_size; i++) {
                        MAGIC.wMem8(i, (byte) 0);
                    }

                    Object eO = MAGIC.cast2Obj(eO_address);
                    MAGIC.assign(eO._r_relocEntries, 3);
                    MAGIC.assign(eO._r_type, MAGIC.clssDesc("EmptyObject"));
                    MAGIC.assign(eO._r_scalarSize, eO_size);

                    if (first_eO == null) {
                        first_eO = (EmptyObject) eO;
                    } else {
                        EmptyObject iter = first_eO;
                        // get last EmptyObject
                        while (iter.nextEmptyObject != null) {
                            iter = iter.nextEmptyObject;
                        }
                        iter.nextEmptyObject = (EmptyObject) eO;
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
        if (first_eO == null) {
            InitEmptyObjects();
        }
        scalarSize = (scalarSize + 3) & ~3; // Align to multiples of 4

        int relocNewObject = relocEntries << 2;
        int sizeRequired = scalarSize + relocNewObject + 16;

        //find next big enough EmptyObject
        EmptyObject eO = first_eO;
        while ((eO._r_scalarSize - 8) < sizeRequired) {
            eO = eO.nextEmptyObject;
            if (eO == null) {
                MAGIC.inline(0xCC);
            }
        }

        int startOfObject = MAGIC.cast2Ref(eO) + eO._r_scalarSize - scalarSize; // Start of current new object
        int endOfObject = startOfObject + relocNewObject + scalarSize; // offset for next new object

        for (int i = startOfObject; i < endOfObject; i += 4) {
            MAGIC.wMem32(i, 0); // init memory for object with 0
        }

        Object newObj = MAGIC.cast2Obj(startOfObject + relocNewObject);
        MAGIC.assign(newObj._r_relocEntries, relocEntries);
        MAGIC.assign(newObj._r_scalarSize, scalarSize);
        MAGIC.assign(newObj._r_type, type);

        if(first_O == null){
            first_O = newObj;
            last_O = newObj;
        }

        MAGIC.assign(last_O._r_next, newObj);
        last_O = newObj;
        MAGIC.assign(newObj._r_next, eO._r_next);

        MAGIC.assign(eO._r_scalarSize, eO._r_scalarSize - sizeRequired); // shrink emptyObject by size of new object
        return newObj;
    }

    public static int countObjects() {
        int count = 1;
        if(first_O != null) {
            Object iter = first_O;

            while (iter._r_next != null) {
                count++;
                iter = iter._r_next;
            }
        }
        return count;
    }

    public static int countEmptyObjects() {
        int count = 1;
        if(first_eO != null) {
            EmptyObject iter = first_eO;

            while (iter.nextEmptyObject != null) {
                count++;
                iter = iter.nextEmptyObject;
            }
        }
        return count;
    }

    public static SArray newArray(int length, int arrDim, int entrySize, int stdType,
                                  SClassDesc unitType) { //unitType is not for sure of type SClassDesc
        int scS, rlE;
        SArray me;

        if (stdType == 0 && unitType._r_type != MAGIC.clssDesc("SClassDesc"))
            MAGIC.inline(0xCC); //check type of unitType, we don't support interface arrays
        scS = MAGIC.getInstScalarSize("SArray");
        rlE = MAGIC.getInstRelocEntries("SArray");
        if (arrDim > 1 || entrySize < 0) rlE += length;
        else scS += length * entrySize;
        me = (SArray) newInstance(scS, rlE, MAGIC.clssDesc("SArray"));
        MAGIC.assign(me.length, length);
        MAGIC.assign(me._r_dim, arrDim);
        MAGIC.assign(me._r_stdType, stdType);
        MAGIC.assign(me._r_unitType, unitType);
        return me;
    }

    public static void newMultArray(SArray[] parent, int curLevel, int destLevel,
                                    int length, int arrDim, int entrySize, int stdType, SClassDesc clssType) {
        int i;

        if (curLevel + 1 < destLevel) { //step down one level
            curLevel++;
            for (i = 0; i < parent.length; i++) {
                newMultArray((SArray[]) ((Object) parent[i]), curLevel, destLevel,
                        length, arrDim, entrySize, stdType, clssType);
            }
        } else { //create the new entries
            destLevel = arrDim - curLevel;
            for (i = 0; i < parent.length; i++) {
                parent[i] = newArray(length, destLevel, entrySize, stdType, clssType);
            }
        }
    }

    public static boolean isInstance(Object o, SClassDesc dest, boolean asCast) {
        SClassDesc check;

        if (o == null) {
            if (asCast) return true; //null matches all
            return false; //null is not an instance
        }
        check = o._r_type;
        while (check != null) {
            if (check == dest) return true;
            check = check.parent;
        }
        if (asCast) MAGIC.inline(0xCC);
        return false;
    }

    public static SIntfMap isImplementation(Object o, SIntfDesc dest, boolean asCast) {
        SIntfMap check;

        if (o == null) return null;
        check = o._r_type.implementations;
        while (check != null) {
            if (check.owner == dest) return check;
            check = check.next;
        }
        if (asCast) MAGIC.inline(0xCC);
        return null;
    }

    public static boolean isArray(SArray o, int stdType, SClassDesc clssType, int arrDim, boolean asCast) {
        SClassDesc clss;

        //in fact o is of type "Object", _r_type has to be checked below - but this check is faster than "instanceof" and conversion
        if (o == null) {
            if (asCast) return true; //null matches all
            return false; //null is not an instance
        }
        if (o._r_type != MAGIC.clssDesc("SArray")) { //will never match independently of arrDim
            if (asCast) MAGIC.inline(0xCC);
            return false;
        }
        if (clssType == MAGIC.clssDesc("SArray")) { //special test for arrays
            if (o._r_unitType == MAGIC.clssDesc("SArray"))
                arrDim--; //an array of SArrays, make next test to ">=" instead of ">"
            if (o._r_dim > arrDim) return true; //at least one level has to be left to have an object of type SArray
            if (asCast) MAGIC.inline(0xCC);
            return false;
        }
        //no specials, check arrDim and check for standard type
        if (o._r_stdType != stdType || o._r_dim < arrDim) { //check standard types and array dimension
            if (asCast) MAGIC.inline(0xCC);
            return false;
        }
        if (stdType != 0) {
            if (o._r_dim == arrDim) return true; //array of standard-type matching
            if (asCast) MAGIC.inline(0xCC);
            return false;
        }
        //array of objects, make deep-check for class type (PicOS does not support interface arrays)
        if (o._r_unitType._r_type != MAGIC.clssDesc("SClassDesc")) MAGIC.inline(0xCC);
        clss = o._r_unitType;
        while (clss != null) {
            if (clss == clssType) return true;
            clss = clss.parent;
        }
        if (asCast) MAGIC.inline(0xCC);
        return false;
    }

    public static void checkArrayStore(SArray dest, SArray newEntry) {
        if (dest._r_dim > 1) isArray(newEntry, dest._r_stdType, dest._r_unitType, dest._r_dim - 1, true);
        else if (dest._r_unitType == null) MAGIC.inline(0xCC);
        else isInstance(newEntry, dest._r_unitType, true);
    }

    public static void nullException() {
        MAGIC.inline(0xCC);
    }
}