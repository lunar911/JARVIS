package interrupt;

import helpers.*;
import peripheral.Keyboard;
import peripheral.StaticV24;
import screen.Screen;
import virtualmemory.MMU;

public class InterruptHandler {

    private static final int interruptAdressStart = 0x07E00, MASTER =
            0x20, SLAVE = 0xA0;
    private static final int currentAdress = interruptAdressStart;

    public static void initPic() {
        createIDT();

        loadIDT(interruptAdressStart, 8 * 48);

        programmChip(MASTER, 0x20, 0x04); //init offset and slave config of master
        programmChip(SLAVE, 0x28, 0x02); //init offset and slave config of slave

        MAGIC.inline(0xFB); // enable ALL interrupts
    }

    private static void createIDT() {
        createIDTEntry(0x0, MAGIC.mthdOff("InterruptHandler", "divideError"));
        createIDTEntry(0x1, MAGIC.mthdOff("InterruptHandler", "debugException"));
        createIDTEntry(0x2, MAGIC.mthdOff("InterruptHandler", "NMI"));
        createIDTEntry(0x3, MAGIC.mthdOff("InterruptHandler", "breakpoint"));
        createIDTEntry(0x4, MAGIC.mthdOff("InterruptHandler", "INTO_Overflow"));
        createIDTEntry(0x5, MAGIC.mthdOff("InterruptHandler", "indexOutOfRange"));
        createIDTEntry(0x6, MAGIC.mthdOff("InterruptHandler", "invalidOpcode"));
        createIDTEntry(0x7, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x8, MAGIC.mthdOff("InterruptHandler", "doubleFault"));
        createIDTEntry(0x9, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0xA, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0xB, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0xC, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(
                0xD,
                MAGIC.mthdOff("InterruptHandler", "generalProtectionError")
        );
        createIDTEntry(0xE, MAGIC.mthdOff("InterruptHandler", "pageFault"));
        createIDTEntry(0xF, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x10, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x11, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x12, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x13, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x14, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x15, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x16, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x17, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x18, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x19, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x1A, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x1B, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x1C, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x1D, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x1E, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x1F, MAGIC.mthdOff("InterruptHandler", "nohandle")); // reserved
        createIDTEntry(0x20, MAGIC.mthdOff("InterruptHandler", "timer"));
        createIDTEntry(0x21, MAGIC.mthdOff("InterruptHandler", "keyBoard"));
        createIDTEntry(0x22, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x23, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x24, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x25, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x26, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x27, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x28, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x29, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x2A, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x2B, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x2C, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x2D, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x2E, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
        createIDTEntry(0x2F, MAGIC.mthdOff("InterruptHandler", "nohandle")); // further devices
    }

    private static void createIDTEntry(int interruptOffset, int handlerOffset) {
        InterruptDescriptor iDescriptor = (InterruptDescriptor) MAGIC.cast2Struct(
                currentAdress + interruptOffset * 8
        );
        int handlerAdress = getHandlerAdress(handlerOffset);
        iDescriptor.firstOffset = (short) ((handlerAdress & 0xFFFF0000) >>> 16);
        iDescriptor.segmentSelector = (short) 8;
        iDescriptor.configurationBits = (short) 0x8E00;
        iDescriptor.secondOffset = (short) (handlerAdress & 0xFFFF);
    }

    private static int getHandlerAdress(int handlerOffset) {
        int handlerMemAddress =
                MAGIC.cast2Ref(MAGIC.clssDesc("InterruptHandler")) + handlerOffset;
        int handlerRefAddress = MAGIC.rMem32(handlerMemAddress);
        int handlerReference = handlerRefAddress + MAGIC.getCodeOff();
        return handlerReference;
    }

    private static void programmChip(int port, int offset, int icw3) {
        MAGIC.wIOs8(port++, (byte) 0x11); // ICW1
        MAGIC.wIOs8(port, (byte) offset); // ICW2
        MAGIC.wIOs8(port, (byte) icw3); // ICW3
        MAGIC.wIOs8(port, (byte) 0x01); // ICW4
    }

    private static void loadIDT(int baseAddress, int tableLimit) {
        long tmp = (((long) baseAddress) << 16) | (long) tableLimit;
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp); // lidt [ebp-0x08/tmp]
    }

    public static void loadInterruptDescriptorTableRealMode() {
        int tableLimit = 4 * 48; // Byte count in table
        long tmp = (long) 0 | (long) tableLimit; // 0 is the table base address
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp); // lidt [ebp-0x08/tmp]
    }

    @SJC.Interrupt
    public static void nohandle() {
        Screen.printStatic("nohandle");
    }

    @SJC.Interrupt
    public static void divideError() {
        Screen.printStatic("divideError");
    }

    @SJC.Interrupt
    public static void debugException() {
        Screen.printStatic("debugException");
    }

    @SJC.Interrupt
    public static void NMI() {
        Screen.printStatic("NMI");
    }

    // https://wiki.osdev.org/Stack_Trace
    @SJC.Interrupt
    public static void breakpoint() {
        int ebp = 0;
        MAGIC.inline(0x89, 0x6D);
        MAGIC.inlineOffset(1, ebp); //mov [ebp+xx],ebp

        Bluescreen.createBlueScreen(ebp);
    }

    @SJC.Interrupt
    public static void INTO_Overflow() {
        Screen.printStatic("INTO_Overflow");
    }

    @SJC.Interrupt
    public static void indexOutOfRange() {
        Screen.printStatic("indexOutOfRange");
    }

    @SJC.Interrupt
    public static void invalidOpcode() {
        Screen.printStatic("invalidOpcode");
    }

    @SJC.Interrupt
    public static void doubleFault() {
        Screen.printStatic("doubleFault");
    }

    @SJC.Interrupt
    public static void generalProtectionError() {
        Screen.printStatic("generalProtectionError");
    }

    @SJC.Interrupt
    public static void pageFault(int errorCode) {
        Screen.blueScreen();
        Screen.directPrintString("PageFault at: ", 0, 0, 0x07);

        int pageAddress = MMU.getCR2();
        Screen.directPrintInt(pageAddress,16, 19, 0, 0x07);


        StaticV24.print(errorCode);
        StaticV24.println();
        StaticV24.printHex(pageAddress, 8);


        if ((errorCode & 0x01) > 0) {
            Screen.directPrintString("Page is present.", 0, 1, 0x07);
        } else {
            Screen.directPrintString("Page not present.", 0, 1, 0x07);
        }

        if ((errorCode & 0x02) > 0) {
            Screen.directPrintString("Page is writable.", 0, 2, 0x07);
        } else {
            Screen.directPrintString("Page not writable.", 0, 2, 0x07);
        }

        if ((errorCode & 0x04) > 0) {
            Screen.directPrintString("Supervisor set.", 0, 3, 0x07);
        } else {
            Screen.directPrintString("Supervisor not set.", 0, 3, 0x07);
        }

        if ((errorCode & 0x08) > 0) {
            Screen.directPrintString("Write Through set.", 0, 4, 0x07);
        } else {
            Screen.directPrintString("Write Through not set.", 0, 4, 0x07);
        }

        if ((errorCode & 0x10) > 0) {
            Screen.directPrintString("Cache Disabled set.", 0, 5, 0x07);
        } else {
            Screen.directPrintString("Cache Disabled not set.", 0, 5, 0x07);
        }

        if ((errorCode & 0x20) > 0) {
            Screen.directPrintString("Accessed set.", 0, 6, 0x07);
        } else {
            Screen.directPrintString("Accessed not set.", 0, 6, 0x07);
        }

        if ((errorCode & 0x40) > 0) {
            Screen.directPrintString("Dirty set.", 0, 7, 0x07);
        } else {
            Screen.directPrintString("Dirty not set.", 0, 7, 0x07);
        }

        while (true) ;
    }

    @SJC.Interrupt
    public static void timer() {
        Time.now += 1;
        MAGIC.wIOs8(MASTER, (byte) 0x20);
    }

    @SJC.Interrupt
    public static void keyBoard() {
        Keyboard.processKeyEvent();
        MAGIC.wIOs8(MASTER, (byte) 0x20);
    }
}
