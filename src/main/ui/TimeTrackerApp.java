package ui;

import model.Course;
import model.CourseGroup;
import model.TimeTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Study Time tracker
public class TimeTrackerApp {
    private static final String JSON_STORE = "./data/tracker.json";
    private TimeTracker tracker;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes and runs the time tracker
    public TimeTrackerApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        tracker = new TimeTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTimeTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTimeTracker() {
        boolean keepGoing = true;

        System.out.println("Welcome to the Study Time Tracker!");

        while (keepGoing) {
            displayMenu();
            input = new Scanner(System.in);
            String command = input.nextLine().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Goodbye!");
    }

    // EFFECTS: displays the main menu
    private void displayMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("1 -> View your course groups");
        System.out.println("2 -> Create a new course group");
        System.out.println("3 -> Add a course to a course group");
        System.out.println("4 -> Remove a course from a course group");
        System.out.println("5 -> Increase study hours for a course");
        System.out.println("6 -> Save your time tracker");
        System.out.println("7 -> Load your time tracker");
        System.out.println("q -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes the user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewGroups();
        } else if (command.equals("2")) {
            createCourseGroup();
        } else if (command.equals("3")) {
            addCourseToGroup();
        } else if (command.equals("4")) {
            removeCourseFromGroup();
        } else if (command.equals("5")) {
            increaseStudyHours();
        } else if (command.equals("6")) {
            saveTracker();
        } else if (command.equals("7")) {
            loadTracker();
        } else {
            System.out.println("Invalid command.");
        }
    }

    // EFFECTS: show all groups with their courses
    private void viewGroups() {
        if (tracker.getGroups().isEmpty()) {
            System.out.println("No course groups.");
        } else {
            for (CourseGroup group : tracker.getGroups()) {
                System.out.println("Group: " + group.getGroupName());
                if (group.getCourses().isEmpty()) {
                    System.out.println("No course has been added to this group.");
                } else {
                    for (Course c : group.getCourses()) {
                        System.out.println(" - " + c.getName() + "\tStudy Hours: " + c.getStudyHours());
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new course group unless the group name already exists
    private void createCourseGroup() {
        System.out.print("Enter a name for the new course group: ");
        String name = input.nextLine();

        if (tracker.findGroup(name) == null) {
            tracker.addGroup(new CourseGroup(name));
            System.out.println("Course group '" + name + "' has been created.");
        } else {
            System.out.println("A group with the name '" + name + "' already exists.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new course to the specified group
    // unless the course has been added
    private void addCourseToGroup() {
        System.out.print("Enter course group name: ");
        String groupName = input.nextLine();
        CourseGroup group = tracker.findGroup(groupName);

        if (group != null) {
            System.out.print("Enter course name: ");
            String courseName = input.nextLine();

            if (group.findCourse(courseName) == null) {
                group.addCourse(new Course(courseName));
                System.out.println("Course '" + courseName + "' has been added to group '" + groupName + "'.");
            } else {
                System.out.println(
                        "A course with the name '" + courseName + "' already exists in group '" + groupName + "'.");
            }
        } else {
            System.out.println("Group not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a course from the specified group
    private void removeCourseFromGroup() {
        System.out.print("Enter course group name: ");
        String groupName = input.nextLine();
        CourseGroup group = tracker.findGroup(groupName);

        if (group != null) {
            System.out.print("Enter course name: ");
            String courseName = input.nextLine();
            Course course = group.findCourse(courseName);

            if (course != null) {
                group.removeCourse(courseName);
                System.out.println("Course '" + courseName + "' has been removed from group '" + groupName + "'.");
            } else {
                System.out.println("Course '" + courseName + "' does not exist in group '" + groupName + "'.");
            }
        } else {
            System.out.println("Group not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: increases study hours for a specified course
    private void increaseStudyHours() {
        System.out.print("Enter course group name: ");
        String groupName = input.nextLine();
        CourseGroup group = tracker.findGroup(groupName);

        if (group != null) {
            System.out.print("Enter course name: ");
            String courseName = input.nextLine();
            Course course = group.findCourse(courseName);

            extracted(courseName, course);
        } else {
            System.out.println("Group not found.");
        }
    }

    private void extracted(String courseName, Course course) {
        if (course != null) {
            System.out.print("Enter hours to add: ");
            double hours = Double.parseDouble(input.nextLine());

            if (hours < 0) {
                System.out.println("Invalid input. Added hours must not be negative");
            } else {
                course.addHours(hours);
                System.out.println(hours + " hours has been added to " + courseName + ".");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    // EFFECTS: saves the workroom to file
    public void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
            System.out.println("Your study time tracker has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads tracker from file
    public void loadTracker() {
        try {
            tracker = jsonReader.read();
            System.out.println("Your study time tracker has been loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
