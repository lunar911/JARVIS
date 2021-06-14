package bomberman;

import peripheral.Key;
import peripheral.Keyboard;


public class KeyController {
    public KeyController() {

    }

    public int getPlayerKey() {
        if (Keyboard.isNewKeyEvent()) {
            int key = Keyboard.getKey();

            while (Keyboard.isNewKeyEvent()) Keyboard.getKey(); // pop all keys

            return key;
        } else {
            return Key.NOKEY;
        }
    }
}
