package rte;

import bios.BIOS;

public class StaticMemoryMap {
    public static long startAddress = 0;
    public static long size = 0;
    public static int type = 0;


    private static final int buffer = 0x8000;

    public static boolean next() {
        BIOS.regs.EAX = 0x0000E820;
        BIOS.regs.EDX = 0x534D4150; // SMAP

        // store result in
        BIOS.regs.EDI = buffer;
        // buffer size >= 20 bytes
        BIOS.regs.ECX = 24;

        BIOS.rint(0x15);
        if (BIOS.regs.EAX != 0x534D4150 || BIOS.regs.EBX == 0) return false;

        startAddress = MAGIC.rMem64(buffer);
        size = MAGIC.rMem64(buffer + 8) - startAddress;
        type = MAGIC.rMem32(buffer + 16);

        return true;
    }
}