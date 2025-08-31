package persistance;

import model.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkCourse(String name, double hours, Course course) {
        assertEquals(name, course.getName());
        assertEquals(hours, course.getStudyHours());
    }
}
