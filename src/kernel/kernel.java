package kernel;

import screen.*;

public class Kernel {

  static final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
  static final String[] wordArray = { "Hallo", "ihr", "da", "draussen", "!" };

  public static void main() {
    Screen.clearScreen();

    Screen screen = new Screen();

    for (int i = 0; i < 3000; i++) {
      if (i < 2000) {
        screen.print(0);
      } else {
        screen.print(1);
      }
    }

    /*
    //screen.printHex(9);
    screen.printHex(100);
    screen.printHex(71);
    screen.printHex(11);
    screen.printHex(12);
    
    
    for(int i = 0; i < 15; i++) {
      screen.printHex(i);
    }

    for(int i = 33; i < 128; i++) {
      screen.print((char) i);
    }

    for(int i = 0; i < 5; i++) {
      screen.print(wordArray[i]);
      screen.print(' ');
    }*/

    while (true);
  }

  public static void testScreen() {}
}
