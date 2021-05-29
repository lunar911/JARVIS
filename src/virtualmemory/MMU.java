package virtualmemory;

import helpers.Time;
import peripheral.StaticV24;
import rte.SClassDesc;
import rte.StaticMemoryMap;

public class MMU {
    final int pagedirBaseAdress = 0x12000;

    public MMU() {
        int pageTableStart = align4kBAddress(MAGIC.imageBase + MAGIC.rMem32(MAGIC.imageBase + 4));

        // Create Pagedir
        PageDir dir = (PageDir) MAGIC.cast2Struct(pagedirBaseAdress);

        for(int i = 0; i < 1024; i++) {
            dir.tables[i] = ((i << 12) + pageTableStart) | 0x03;
        }


        // Create Tables
        int pageCounter = 0;
        for(int i = 0; i < 1024; i++) {
            PageTable table = (PageTable) MAGIC.cast2Struct(pageTableStart + 1024 * 4 * i);
            for(int j = 0; j < 1024; j++) {
                table.pages[j] = (pageCounter << 12) | 0x3;
                pageCounter++;
            }
        }
        //modify last and first page
        PageTable firstTable = (PageTable) MAGIC.cast2Struct(pageTableStart + 1024 * 4);
        firstTable.pages[0] = 0;


        PageTable lastTable = (PageTable) MAGIC.cast2Struct(pageTableStart + 1024 * 4 * 1023);
        lastTable.pages[1023] = 0;

        setCR3(pagedirBaseAdress);

        enableVirtualMemory();
    }

    public int align4kBAddress(int address) {
        return address + 4096 & ~4096; // align to 4kB.
    }


    public void setCR3(int pagedirStart) {
        MAGIC.inline(0x8B, 0x45);
        MAGIC.inlineOffset(1, pagedirStart); //mov eax,[ebp+8]
        MAGIC.inline(0x0F, 0x22, 0xD8); //mov cr3,eax
    }

    public void resetCR3(int pagedirStart) {
        setCR3(pagedirStart);
    }

    public void enableVirtualMemory() {
        MAGIC.inline(0x0F, 0x20, 0xC0); //mov eax,cr0
        MAGIC.inline(0x0D, 0x00, 0x00, 0x01, 0x80); //or eax,0x80010000
        MAGIC.inline(0x0F, 0x22, 0xC0); //mov cr0,eax
    }

    public int getCR2() {
        int cr2 = 0;
        MAGIC.inline(0x0F, 0x20, 0xD0); //mov e/rax,cr2
        MAGIC.inline(0x89, 0x45);
        MAGIC.inlineOffset(1, cr2); //mov [ebp-4],eax
        return cr2;
    }
}
