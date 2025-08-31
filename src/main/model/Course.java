package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a course with a name and total hours studied
public class Course implements Writable {
    private String name; // the name of the course
    private double studyHours; // the total study hours of the course

    // Constructs a new course.
    // Requires: name has a non-zero length
    // Effects: assign the course with given name and 0 study hours
    public Course(String name) {
        this.name = name;
        studyHours = 0;
    }

    // Effects: return a string of the course name and its study hour.
    public String viewCourse(Course c) {
        return " - " + c.getName() + "\tStudy Hours: " + c.getStudyHours();
    }

    // Adds the study hours of the course.
    // Requires: hours >= 0
    // Modifies: this
    // Effects: Increases studyHours by hours
    public void addHours(double hours) {
        studyHours += hours;
        EventLog.getInstance().logEvent(new Event(hours + " hours added to course '" + name + "'."));
    }

    // getter
    public String getName() {
        return name;
    }

    // getter
    public double getStudyHours() {
        return studyHours;
    }

    // Effects: returns a JSONObject representing the Course
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("studyHours", studyHours);
        return json;
    }
}