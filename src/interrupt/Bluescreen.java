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
            String method = getMethodName(eip);
            StaticV24.println(method);
            //Screen.directPrintString(method, 20, count, 0x07);
            while(true);
            nextEBP = MAGIC.rMem32(nextEBP);
            count++;
        }
        while (true) ;
    }

    private static String getMethodName(int eip) {
        String p = loopPackages(eip, SPackage.root);
        if (p != null) {
            StaticV24.println(p);
        } else {
            StaticV24.println("oooooooooowtf");
        }

        return p;
    }


    private static String loopPackages(int eip, SPackage p) {
        String className = loopClasses(eip, p.units);
        StaticV24.println(p.name);
        if (className != null) {
            StaticV24.println(className);
            return p.name;
        }

        if (p.subPacks != null) {
            className = loopPackages(eip, p.subPacks);
            if (className != null) {
                StaticV24.println(className);
                return p.name;
            }
        }

        if (p.nextPack != null)
            return loopPackages(eip, p.nextPack);
        return null;
    }

    private static String loopClasses(int eip, SClassDesc c) {
        while (c != null) {
            StaticV24.print('-');
            StaticV24.println(c.name);
            String mthd = loopMethods(eip, c.mthds);
            if (mthd != null) {
                StaticV24.println(mthd);
                return c.name;
            }

            c = c.nextUnit;
        }

        return null;
    }

    private static String loopMethods(int eip, SMthdBlock m) {
        while (m != null) {
            //StaticV24.print("--");
            //StaticV24.println(m.namePar);
            if (inRange(eip, m))
                return m.namePar;
            m = m.nextMthd;
        }

        return null;
    }

    private static boolean inRange(int eip, SMthdBlock m) {
        int start = MAGIC.cast2Ref(m);
        int end = start + m._r_scalarSize;
        return (start <= eip) && (eip < end);
    }
}
