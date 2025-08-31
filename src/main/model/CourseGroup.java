package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a group of courses
public class CourseGroup implements Writable {
    private String groupName; // name of the group
    private ArrayList<Course> courses; // the list of courses in the group

    // Constructs a new course group.
    // Requires: groupName has a non-zero length
    // Effects: create a empty list of the course group with given name
    public CourseGroup(String groupName) {
        this.groupName = groupName;
        courses = new ArrayList<>();
    }

    // Adds a course to this group.
    // Modifies: this
    // Effects: Adds the course to the course group if it's not already in it,
    // otherwise do nothing
    public void addCourse(Course course) {
        for (Course c : courses) {
            if (c.getName().equals(course.getName())) {
                return;
            }
        }
        courses.add(course);
        EventLog.getInstance()
                .logEvent(new Event("Course '" + course.getName() + "' added to group '" + groupName + "'."));
    }

    // Removes a course from this group by name.
    // Modifies: this
    // Effects: Removes the course from the course group if it's already in it,
    // otherwise do nothing
    public void removeCourse(String name) {
        Course toRemove = null;
        for (Course c : courses) {
            if (c.getName().equals(name)) {
                toRemove = c;
                break;
            }
        }
        courses.remove(toRemove);
        EventLog.getInstance()
                .logEvent(new Event("Course '" + name + "' removed from group '" + groupName + "'."));
    }

    // getter
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // getter
    public String getGroupName() {
        return groupName;
    }

    // EFFECTS: returns the course with the given name, or null if not found
    public Course findCourse(String name) {
        for (Course c : courses) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    // Effects: returns a JSONObject representing the CourseGroup
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("groupName", groupName);
        json.put("courses", coursesToJson());
        return json;
    }

    // Effects: returns a JSONArray representing the list of courses
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course course : courses) {
            jsonArray.put(course.toJson());
        }

        return jsonArray;
    }
}