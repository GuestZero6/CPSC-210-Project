package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents the time tracker: a group of course groups
public class TimeTracker implements Writable {
    private ArrayList<CourseGroup> groups; // the list of all course groups

    // Constructs a new study time tracker.
    // Effects: create a empty list of course groups
    public TimeTracker() {
        groups = new ArrayList<>();
    }

    // Adds a new course group.
    // Modifies: this
    // Effects: Adds the group to the tracker if it's not already in it,
    // otherwise do nothing
    public void addGroup(CourseGroup group) {
        for (CourseGroup g : groups) {
            if (g.getGroupName().equals(group.getGroupName())) {
                return;
            }
        }
        groups.add(group);
        EventLog.getInstance().logEvent(
                new Event("Course group '" + group.getGroupName() + "' added to time tracker."));
    }

    // Removes a course group by name.
    // Modifies: this
    // Effects: Removes the course group from the tracker if it's in it,
    // otherwise do nothing
    public void removeGroup(String groupname) {
        CourseGroup toRemove = null;
        for (CourseGroup g : groups) {
            if (g.getGroupName().equals(groupname)) {
                toRemove = g;
                break;
            }
        }
        groups.remove(toRemove);
    }

    // getter
    public ArrayList<CourseGroup> getGroups() {
        return groups;
    }

    // EFFECTS: returns the group with the given name, or null if not found
    public CourseGroup findGroup(String name) {
        for (CourseGroup group : groups) {
            if (group.getGroupName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    // Effects: returns a JSONObject representing the TimeTracker
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("groups", groupsToJson());
        return json;
    }

    // Effects: returns a JSONObject representing the list of groups
    private JSONArray groupsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CourseGroup group : groups) {
            jsonArray.put(group.toJson());
        }

        return jsonArray;
    }
}
