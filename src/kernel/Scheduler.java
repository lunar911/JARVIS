package kernel;

import applications.Editor;
import applications.Moon;
import rte.MarkAndSweep;
import screen.Screen;
import terminal.Terminal;


public class Scheduler {
    private final Task[] tasks;
    private final int taskcount;

    public Scheduler() {
        taskcount = 4;
        Screen screen = new Screen();
        tasks = new Task[taskcount];
        tasks[0] = new Terminal(screen);
        tasks[1] = new Moon();
        tasks[2] = new Editor(screen);
        tasks[3] = new MarkAndSweep(screen);
    }

    public void loop() {
        while (true) {
            for (int i = 0; i < taskcount; i++) {
                tasks[i].run();
                Screen.clearScreen();
            }
        }
    }
}
