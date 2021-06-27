package interrupt;

import peripheral.StaticV24;

public class Mouse {

    private static final int mouse_cycle = 0;
    private static final int mouseBytes = 0;
    private static final byte mouse_x = 0;
    private static final byte mouse_y = 0;

    //Mouse functions
    public static void mouseHandler() //struct regs *a_r (not used but just there)
    {
        StaticV24.print("mouse event: 0x");
        StaticV24.printHex(MAGIC.rIOs8(0x60), 2);
        StaticV24.println();
//        switch(mouse_cycle)
//        {
//            case 0:
//                mouse_byte[0]=inportb(0x60);
//                mouse_cycle++;
//                break;
//            case 1:
//                mouse_byte[1]=inportb(0x60);
//                mouse_cycle++;
//                break;
//            case 2:
//                mouse_byte[2]=inportb(0x60);
//                mouse_x=mouse_byte[1];
//                mouse_y=mouse_byte[2];
//                mouse_cycle=0;
//                break;
//        }
    }

    // weird naming scheme?
    // ... probably ...
    @SJC.Inline
    private static boolean isOutputBufferFull() {
        return (MAGIC.rIOs8(0x64) & 1) == 1;
    }

    @SJC.Inline
    private static boolean isInputBufferEmpty() {
        return (MAGIC.rIOs8(0x64) & 2) == 0;
    }

    /**
     * Wait before sending command to mouse.
     * <p>
     * If type == 0: Wait for output buffer to be empty
     * If type == 1: Wait for bit 1 in 0x64 to be clear and we can send stuff to 0x60
     */
    public static void mouseWait(int type) {
        int timeOut = 100000;
        if (type == 0) {
            while (timeOut-- > 0) {
                if (isOutputBufferFull()) {
                    return;
                }
            }
        } else {
            while (timeOut-- > 0) {
                if (isInputBufferEmpty()) {
                    return;
                }
            }
        }

        StaticV24.print("Timeout error in mouseWait type ");
        StaticV24.println(type);
    }

    /**
     * Send command to PS/2 device.
     *
     * Command is sent by first writing 0xD4 to the status register, then the command.
     * During this process we always check if the input buffer is empty.
     */
    public static void mouseWrite(int command) {
        // wait for input buffer to be empty
        mouseWait(1);

        // tell mouse that we're sending a command
        MAGIC.wIOs8(0x64, (byte) 0xD4);

        // elevator music starts playing ...
        mouseWait(1);

        // send command
        MAGIC.wIOs8(0x60, (byte) command);
    }

    /**
     * Read byte from data port 0x60
     */
    public static int mouseRead() {
        // wait for output buffer to be set
        mouseWait(0);

        return (int) MAGIC.rIOs8(0x60);
    }

    @SJC.Inline
    private static void writeToStatusRegister(int command) {
        mouseWait(1);
        MAGIC.wIOs8(0x64, (byte) command);
    }

    public static void mouseInstall() {
        int status;

        StaticV24.println("Enabling mouse");
        //Enable the auxiliary mouse device
        writeToStatusRegister(0xAE);
        writeToStatusRegister(0xA8);


        //Enable the interrupts
        MAGIC.wIOs8(0x64, (byte) 0x20);

        mouseWait(0);
        status = MAGIC.rIOs8(0x60) | 2;
        StaticV24.print("Status: ");
        StaticV24.println(status);

        // Write next byte to "byte 0" of internal RAM (Controller Configuration Byte)
        writeToStatusRegister(0x60);

        mouseWait(1);
        MAGIC.wIOs8(0x60, (byte) status);

        // send test command
        mouseWait(1);
        MAGIC.wIOs8(0x64, (byte) 0xAA);
        int result = mouseRead();
        StaticV24.print("Mouse test result (should be 0x55): 0x");
        StaticV24.printHex(result, 2);
        StaticV24.println();

        // interface test
        mouseWait(1);
        MAGIC.wIOs8(0x64, (byte) 0xAB);
        result = mouseRead();
        StaticV24.print("Interface 0xAB result: ");
        StaticV24.printHex(result, 2);
        StaticV24.println();

        mouseWait(1);
        MAGIC.wIOs8(0x64, (byte) 0xA9);
        result = mouseRead();
        StaticV24.print("Interface 0xA9 result: ");
        StaticV24.printHex(result, 2);
        StaticV24.println();

        // check mouse id
        mouseWrite(0xF2);
        StaticV24.print("Mouse ID: ");
        StaticV24.printHex(mouseRead(), 2);
        StaticV24.println();

        mouseWrite(0xF6);
        StaticV24.print("Set defaults result: ");
        StaticV24.printHex(mouseRead(), 2);
        StaticV24.println();

        mouseWrite(0xF4);
        StaticV24.print("Activate mouse result: ");
        StaticV24.printHex(mouseRead(), 2);
        StaticV24.println();

        StaticV24.println("Enabled mouse");
    }
}