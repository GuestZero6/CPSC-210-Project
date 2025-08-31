package persistance;

import model.Course;
import model.CourseGroup;
import model.TimeTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TimeTracker tracker = new TimeTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }  
    }

    @Test
    void testWriterEmptyTimeTracker() {
        try {
            TimeTracker tracker = new TimeTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTimeTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTimeTracker.json");
            tracker = reader.read();
            assertEquals(0, tracker.getGroups().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTimeTracker() {
        try {
            TimeTracker tracker = new TimeTracker();
            CourseGroup g1 = new CourseGroup("group1");
            Course c1 = new Course("CPSC 213");
            c1.addHours(5.5);
            Course c2 = new Course("Math 220");
            c2.addHours(2.0);
            g1.addCourse(c1);
            g1.addCourse(c2);
            tracker.addGroup(g1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTimeTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTimeTracker.json");
            TimeTracker readTracker = reader.read();
            ArrayList<CourseGroup> groups = readTracker.getGroups();
            assertEquals(1, groups.size());

            CourseGroup readGroup = groups.get(0);
            assertEquals("group1", readGroup.getGroupName());
            ArrayList<Course> readCourses = readGroup.getCourses();
            assertEquals(2, readCourses.size());
            checkCourse("CPSC 213", 5.5, readCourses.get(0));
            checkCourse("Math 220", 2.0, readCourses.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
