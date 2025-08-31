package persistence;

import model.Course;
import model.CourseGroup;
import model.TimeTracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads time tracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TimeTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTimeTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TimeTracker from JSON object and returns it
    private TimeTracker parseTimeTracker(JSONObject jsonObject) {
        TimeTracker tracker = new TimeTracker();
        addGroups(tracker, jsonObject);
        return tracker;
    }

    // MODIFIES: tracker
    // EFFECTS: parses groups from JSON object and adds them to tracker
    private void addGroups(TimeTracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("groups");
        for (Object json : jsonArray) {
            JSONObject nextGroup = (JSONObject) json;
            addGroup(tracker, nextGroup);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses group from JSON object and adds it to tracker
    private void addGroup(TimeTracker tracker, JSONObject jsonObject) {
        String groupName = jsonObject.getString("groupName");
        CourseGroup courseGroup = new CourseGroup(groupName);

        JSONArray courses = jsonObject.getJSONArray("courses");
        for (Object json : courses) {
            JSONObject courseJson = (JSONObject) json;
            String name = courseJson.getString("name");
            double hours = courseJson.getDouble("studyHours");
            Course c = new Course(name);
            c.addHours(hours);
            courseGroup.addCourse(c);
        }

        tracker.addGroup(courseGroup);
    }
}
