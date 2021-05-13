package terminal;

import peripheral.Key;
import screen.Screen;
import peripheral.Keyboard;
import helpers.Time;
import applications.*;

public class Terminal {
    private final Screen screen;
    private String[] application;
    private Wordbuffer wordbuffer;

    public Terminal() {
        screen = new Screen();
        Screen.clearScreen();
        setupApplications();
        wordbuffer = new Wordbuffer();
        welcome();
    }

    private void setupApplications() {
        application = new String[3];
        application[0] = "MemoryMap";
        application[1] = "PCIScan";
        application[2] = "cls";
    }

    public Screen getScreen() {
        return screen;
    }

    public void welcome() {
        String[] jarvisPhrases = new String[4];

        jarvisPhrases[0] = "As always sir, a great pleasure watching you work.";
        jarvisPhrases[1] = "Sir, take a deep breath.";
        jarvisPhrases[2] = "Mark 42 inbound.";
        jarvisPhrases[3] = "I am a program. I am without form.";

        screen.println(jarvisPhrases[Time.now % 3]);
    }

    public void printHelp(String command) {
        screen.println("Invalid command. ");
        screen.print(command);
        screen.println();
        screen.println("Available commands are: MemoryMap, PCIScan, cls");
    }

    public void run() {
        int key = 0;
        while (key != Key.F12) {
            if (Keyboard.isNewKeyEvent()) {
                key = Keyboard.getKey();
                switch (key) {
                    case Key.ENTER:
                        String command = wordbuffer.getWord();
                        screen.println();
                        if(command.equals(application[0])) {
                            MemoryMap.printMemLayout(screen);
                        } else if (command.equals(application[1])) {
                            PCIScan.scanPCIBus(screen);
                        } else if (command.equals(application[2])) {
                            Screen.clearScreen();
                        }
                        else {
                            printHelp(command);
                        }
                        break;
                    case Key.BACKSPACE:
                        int pos = screen.getCursorPos() - 1;
                        screen.setCursor(pos);
                        screen.print(' ');
                        screen.setCursor(pos);
                        wordbuffer.removeLast();
                        break;
                    case Key.ESCAPE:
                        MAGIC.inline(0xCC);
                        break;
                    default:
                        if (Keyboard.isPrintable(key)) {
                            screen.print((char) key);
                            wordbuffer.storeChar((char) key);
                        }
                }
            }
        }
    }
}
