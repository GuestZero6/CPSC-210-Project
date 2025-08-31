package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCourse {
    private Course course;

    @BeforeEach
    void runBefore() {
        course = new Course("CPSC 210");
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC 210", course.getName());
        assertEquals(0, course.getStudyHours());
    }

    @Test
    void testAddHours() {
        course.addHours(2);
        assertEquals(2, course.getStudyHours());
    }

    @Test
    void testAddHoursMultipleTimes() {
        course.addHours(1.5);
        course.addHours(2.5);
        assertEquals(4.0, course.getStudyHours());
    }
}