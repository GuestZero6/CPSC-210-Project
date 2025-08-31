package ui;

import model.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Represent the window closing event. 
public class WindowClosing extends WindowAdapter {
    @Override
    // Effect: print out log events in the console
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }
}
