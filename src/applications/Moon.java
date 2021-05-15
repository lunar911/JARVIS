package applications;

import screen.Screen;
import kernel.Task;
import helpers.Time;

public class Moon extends Task{

    public static void draw(Screen screen) {
        Screen.clearScreen();
        screen.println("     *                                                            *");
        screen.println("                             aaaaaaaaaaaaaaaa               *");
        screen.println("                         aaaaaaaaaaaaaaaaaaaaaaaa");
        screen.println("                      aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        screen.println("                    aaaaaaaaaaaaaaaaa           aaaaaa");
        screen.println("                  aaaaaaaaaaaaaaaa                  aaaa");
        screen.println("                 aaaaaaaaaaaaa aa                      aa");
        screen.println("*               aaaaaaaa      aa                         a");
        screen.println("                aaaaaaa aa aaaa");
        screen.println("          *    aaaaaaaaa     aaa");
        screen.println("               aaaaaaaaaaa aaaaaaa                               *");
        screen.println("               aaaaaaa    aaaaaaaaaa");
        screen.println("               aaaaaa a aaaaaa aaaaaa");
        screen.println("                aaaaaaa  aaaaaaa");
        screen.println("                aaaaaaaa                                 a");
        screen.println("                 aaaaaaaaaa                            aa");
        screen.println("                  aaaaaaaaaaaaaaaa                  aaaa");
        screen.println("                    aaaaaaaaaaaaaaaaa           aaaaaa        *");
        screen.println("      *               aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        screen.println("                         aaaaaaaaaaaaaaaaaaaaaaaa");
        screen.println("                      *      aaaaaaaaaaaaaaaa");
        Time.wait(30);
    }

    public void run() {
        Screen screen = new Screen();
        draw(screen);
    }
}
