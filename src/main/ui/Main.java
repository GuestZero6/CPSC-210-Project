package ui;

import java.io.FileNotFoundException;

// Start the application
public class Main {
    public static void main(String[] args) {
        try {
            new TimeTrackerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
