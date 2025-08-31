package persistance;

import model.CourseGroup;
import model.TimeTracker;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TimeTracker tt = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTimeTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTimeTracker.json");
        try {
            TimeTracker tt = reader.read();
            assertEquals(0, tt.getGroups().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTimeTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTimeTracker.json");
        try {
            TimeTracker tt = reader.read();
            ArrayList<CourseGroup> groups = tt.getGroups();
            assertEquals(2, groups.size());

            CourseGroup g1 = groups.get(0);
            assertEquals("term1", g1.getGroupName());
            assertEquals(1, g1.getCourses().size());
            checkCourse("CPSC 210", 3.5, g1.getCourses().get(0));

            CourseGroup g2 = groups.get(1);
            assertEquals("term2", g2.getGroupName());
            assertEquals(1, g2.getCourses().size());
            checkCourse("MATH 200", 5.0, g2.getCourses().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
