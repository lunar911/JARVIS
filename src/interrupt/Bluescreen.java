package interrupt;

import peripheral.StaticV24;
import rte.SClassDesc;
import rte.SMthdBlock;
import rte.SPackage;
import screen.Screen;

public class Bluescreen {

    public static void createBlueScreen(int ebp) {
        Screen.blueScreen();

        Screen.directPrintString("EBP", 0, 0, 0x07);
        Screen.directPrintInt(ebp, 16, 0, 1, 0x07);

        int eip = MAGIC.rMem32(ebp + 4);
        Screen.directPrintString("EIP", 8, 0, 0x07);
        Screen.directPrintInt(eip, 16, 8, 1, 0x07);

        final int stackBegin = 0x9BFFC;

        int count = 1;
        int nextEBP = ebp;
        while (nextEBP < stackBegin && nextEBP > 0) {
            int nextEIP = MAGIC.rMem32(nextEBP + 4);

            Screen.directPrintInt(nextEBP, 16, 0, count, 0x07);
            Screen.directPrintInt(nextEIP, 16, 8, count, 0x07);
            SMthdBlock methodBlock = getMethodName(eip);

            if(methodBlock == null) {
                Screen.directPrintString("no Method found.", 20, count, 0x07);
            } else {
                Screen.directPrintString(methodBlock.namePar, 20, count, 0x07);
            }

            nextEBP = MAGIC.rMem32(nextEBP);
            count++;
        }
        while (true) ;
    }
    public static SMthdBlock getMethodName(int eip) { ;
        return loopPackages(eip, SPackage.root);
    }

    private static SMthdBlock loopPackages(int eip, SPackage p) {
        SMthdBlock method = null;

        while(p != null) {
            StaticV24.println(p.name);
            method = loopClasses(eip, p.units);
            if(method != null)
                break;

            method = loopPackages(eip, p.subPacks);
            if(method != null)
                break;

            p = p.nextPack;
        }

        return method;
    }

    private static SMthdBlock loopClasses(int eip, SClassDesc c) {
        SMthdBlock method = null;
        while(c != null) {
            StaticV24.print("/");
            StaticV24.println(c.name);
            method = loopMethods(eip, c.mthds);
            if(method != null)
                break;

            c = c.nextUnit;
        }

        return method;
    }

    private static SMthdBlock loopMethods(int eip, SMthdBlock m) {
        while(m != null) {
            StaticV24.print("/-/");
            StaticV24.print(m.namePar);
            for(int i = m.namePar.length(); i < 60; i++)
                StaticV24.print(' ');
            if(inRange(eip, m))
                break;
            m = m.nextMthd;
        }

        return m;
    }

    private static boolean inRange(int eip, SMthdBlock m) {
        int start = MAGIC.cast2Ref(m);
        int end = start + m._r_scalarSize;
        StaticV24.print("0x");
        StaticV24.printHex(start,8);
        StaticV24.print(" <= 0x");
        StaticV24.printHex(eip, 8);
        StaticV24.print(" < 0x");
        StaticV24.printHex(end, 8);
        StaticV24.print(" = ");
        StaticV24.println((start <= eip && eip < end) ? "true\n\n\n\n\n\n" : "f");
        return start <= eip && eip < end;
    }
}
