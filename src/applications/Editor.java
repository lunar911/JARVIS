package applications;

import kernel.Task;
import peripheral.Key;
import screen.Screen;
import peripheral.Keyboard;

public class Editor extends Task {
    private Screen screen;
    private char[] text;

    public Editor(Screen screen) {
        this.screen = screen;
        this.text = new char[2000];

        for(int i = 0; i < 2000; i++) {
            text[i] = ' ';
        }
    }

    public void drawText() {
        int pos = screen.getCursorPos();
        screen.setCursor(0);
        for(int i = 0; i < 2000; i++) {
            screen.print(text[i]);
        }
        screen.setCursor(pos);
    }

    public void run() {
        int key = 0;
        while (key != Key.F12) {
            if (Keyboard.isNewKeyEvent()) {
                drawText();
                key = Keyboard.getKey();
                switch (key) {
                    case Key.ENTER:
                        screen.println();
                        break;
                    case Key.BACKSPACE:
                        int pos = screen.getCursorPos() - 1;
                        text[pos] = 0;
                        screen.setCursor(pos);
                        screen.print(' ');
                        screen.setCursor(pos);
                        break;
                    case Key.UP_ARROW:
                        int upPos = screen.getCursorPos() - 80;
                        if(upPos < 0) upPos = screen.getCursorPos();
                        screen.setCursor(upPos);
                        break;
                    case Key.DOWN_ARROW:
                        int downPos = screen.getCursorPos() + 80;
                        if(downPos > 2000) downPos = screen.getCursorPos();
                        screen.setCursor(downPos);
                        break;
                    case Key.RIGHT_ARROW:
                        int rightPos = screen.getCursorPos() + 1;
                        if(rightPos > 2000) rightPos = 0;
                        screen.setCursor(rightPos);
                        break;
                    case Key.LEFT_ARROW:
                        int leftPos = screen.getCursorPos() - 1;
                        if(leftPos < 0) leftPos = 2000;
                        screen.setCursor(leftPos);
                        break;
                    default:
                        if (Keyboard.isPrintable(key)) {
                            text[screen.getCursorPos()] = (char) key;
                            screen.print((char) key);
                        }
                }
            }
        }
    }
}
