package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Represents the main graphical interface for the Time Tracker application.
 * Allows the user to manage course groups, add and remove courses, update study
 * hours,
 * and save/load application state using a simple Swing GUI.
 */
public class TimeTrackerAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/tracker.json";

    private TimeTracker tracker;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private WindowClosing windowClosing = new WindowClosing();

    private JTextArea displayArea;
    private JButton createGroupButton;
    private JButton addCourseButton;
    private JButton removeCourseButton;
    private JButton viewCoursesButton;
    private JButton updateHoursButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton clearButton;

    /**
     * MODIFIES: this
     * EFFECTS: initializes the GUI window of the tracker
     */
    public TimeTrackerAppGUI() {
        tracker = new TimeTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setTitle("Time Tracker");
        setSize(600, 600);
        setLayout(new BorderLayout());
        setupImagePanel();
        setupDisplayArea();
        setupButtons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets up the image panel
     */
    private void setupImagePanel() {
        JPanel imagePanel = new JPanel();
        addImage(imagePanel);
        add(imagePanel, BorderLayout.NORTH);
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets up the display area
     */
    private void setupDisplayArea() {
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * MODIFIES: this
     * EFFECTS: initializes all buttons and adds them to a panel
     */
    private void setupButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2));

        createGroupButton = new JButton("Create New Course Group");
        addCourseButton = new JButton("Add Course to Group");
        removeCourseButton = new JButton("Remove Course from Group");
        viewCoursesButton = new JButton("View all Courses");
        updateHoursButton = new JButton("Update Study Hours");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        clearButton = new JButton("Clear All Displays");

        buttonPanel.add(createGroupButton);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(removeCourseButton);
        buttonPanel.add(viewCoursesButton);
        buttonPanel.add(updateHoursButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
        createListeners();
    }

    /**
     * MODIFIES: this
     * EFFECTS: constructs new listener objects for each JButton
     */
    private void createListeners() {
        createGroupButton.addActionListener(e -> createGroup());
        addCourseButton.addActionListener(e -> addCourse());
        removeCourseButton.addActionListener(e -> removeCourse());
        viewCoursesButton.addActionListener(e -> viewCourses());
        updateHoursButton.addActionListener(e -> updateHours());
        saveButton.addActionListener(e -> saveTracker());
        loadButton.addActionListener(e -> loadTracker());
        clearButton.addActionListener(e -> clearDisplay());
        addWindowListener(windowClosing);
    }

    /**
     * MODIFIES: tracker
     * EFFECTS: creates a new course group if it doesn't already exist
     */
    private void createGroup() {
        String name = prompt("Enter group name:");
        if (name == null) {
            return;
        }
        if (tracker.findGroup(name) == null) {
            tracker.addGroup(new CourseGroup(name));
            display("Course group '" + name + "' has been created.");
        } else {
            display("A group with the name '" + name + "' already exists.");
        }
    }

    /**
     * MODIFIES: tracker
     * EFFECTS: adds a course to an existing group
     */
    private void addCourse() {
        String groupName = prompt("Enter group name:");
        if (groupName == null) {
            return;
        }
        CourseGroup group = tracker.findGroup(groupName);

        if (group != null) {
            String courseName = prompt("Enter course name:");
            if (courseName == null) {
                return;
            }

            if (group.findCourse(courseName) == null) {
                group.addCourse(new Course(courseName));
                display("Course '" + courseName + "' has been added to group '" + groupName + "'.");
            } else {
                display("A course with the name '" + courseName + "' already exists in group '" + groupName + "'.");
            }
        } else {
            display("Group not found.");
        }
    }

    /**
     * MODIFIES: tracker
     * EFFECTS: removes a course from a group
     */
    private void removeCourse() {
        String groupName = prompt("Enter group name:");
        if (groupName == null) {
            return;
        }
        CourseGroup group = tracker.findGroup(groupName);

        if (group != null) {
            String courseName = prompt("Enter course name:");
            if (courseName == null) {
                return;
            }
            Course course = group.findCourse(courseName);

            if (course != null) {
                group.removeCourse(courseName);
                display("Course '" + courseName + "' has been removed from group '" + groupName + "'.");
            } else {
                display("Course '" + courseName + "' does not exist in group '" + groupName + "'.");
            }
        } else {
            display("Group not found.");
        }
    }

    /**
     * MODIFIES: none
     * EFFECTS: displays all courses and their study hours for all groups.
     */
    private void viewCourses() {
        if (tracker.getGroups().isEmpty()) {
            display("No course groups.");
        } else {
            for (CourseGroup group : tracker.getGroups()) {
                display("Group: " + group.getGroupName());
                if (group.getCourses().isEmpty()) {
                    display("No course has been added to this group.");
                } else {
                    for (Course c : group.getCourses()) {
                        display(c.viewCourse(c));
                    }
                }
            }
        }
    }


    /**
     * MODIFIES: tracker
     * EFFECTS: adds study hours to the selected course
     */
    private void updateHours() {
        String groupName = prompt("Enter group name:");
        if (groupName == null) {
            return;
        }
        CourseGroup group = tracker.findGroup(groupName);

        if (group != null) {
            String courseName = prompt("Enter course name:");
            if (courseName == null) {
                return;
            }
            Course course = group.findCourse(courseName);

            updateCourse(courseName, course);
        } else {
            display("Group not found.");
        }
    }

    private void updateCourse(String courseName, Course course) {
        if (course != null) {
            double hours = Double.parseDouble(prompt("Enter hours to add:"));

            if (hours < 0) {
                display("Invalid input. Added hours must not be negative");
            } else {
                course.addHours(hours);
                display(hours + " hours has been added to " + courseName + ".");
            }
        } else {
            display("Course not found.");
        }
    }

    /**
     * EFFECTS: saves the current tracker to a JSON file
     */
    private void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
            display("Your study time tracker has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            display("Unable to write to file: " + JSON_STORE);
        }
    }

    /**
     * MODIFIES: tracker
     * EFFECTS: loads the tracker from the JSON file
     */
    private void loadTracker() {
        try {
            tracker = jsonReader.read();
            display("Your study time tracker has been loaded from " + JSON_STORE);
        } catch (IOException e) {
            display("Unable to read from file: " + JSON_STORE);
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets displayArea to show the given text
     */
    private void display(String text) {
        displayArea.append(text + "\n");
    }

    /**
     * MODIFIES: this
     * EFFECTS: clear the displayArea
     */
    private void clearDisplay() {
        displayArea.setText("");
    }

    /**
     * EFFECTS: prompts user with dialog
     */
    private String prompt(String message) {
        return JOptionPane.showInputDialog(this, message);
    }

    /**
     * MODIFIES: this
     * EFFECTS: Adds an image to the GUI panel
     */
    private void addImage(JPanel tracker) {
        String sep = System.getProperty("file.separator");
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "TimeTracker.png");
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        tracker.add(imageLabel);

    }

    /**
     * EFFECTS: launches the Time Tracker application
     */
    public static void main(String[] args) {
        new TimeTrackerAppGUI();
    }
}
