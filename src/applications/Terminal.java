package applications;

import peripheral.Key;
import screen.Screen;
import peripheral.Keyboard;

public class Terminal {
    private final Screen screen;
    private String[] application;

    public Terminal() {
        screen = new Screen();
        Screen.clearScreen();
        setupApplications();
    }

    private void setupApplications() {
        application = new String[2];
        application[0] = "MemoryMap";
        application[1] = "PCIScan";
    }

    public Screen getScreen() {
        return screen;
    }

    public void run() {
        int key = 0;
        while (key != Key.F12) {
            if (Keyboard.isNewKeyEvent()) {
                key = Keyboard.getKey();
                switch (key) {
                    case Key.ENTER:

                        screen.println();
                        break;
                    case Key.ESCAPE:
                        MAGIC.inline(0xCC);
                        break;
                    default:
                        if (Keyboard.isPrintable(key)) screen.print((char) key);
                }
            }
        }
    }
}
