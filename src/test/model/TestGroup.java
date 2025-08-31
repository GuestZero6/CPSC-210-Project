package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGroup {
    private CourseGroup testGroup;
    private Course course1;
    private Course course2;

    @BeforeEach
    void runBefore() {
        testGroup = new CourseGroup("Term 1");
        course1 = new Course("CPSC 210");
        course2 = new Course("MATH 200");
    }

    @Test
    void testConstructor() {
        assertEquals(testGroup.getGroupName(), "Term 1");
        assertEquals(testGroup.getCourses().size(), 0);
    }

    @Test
    void testAddCourseNotThere() {
        checkEmpty();
        testGroup.addCourse(course1);
        assertEquals(testGroup.getCourses().size(), 1);
        assertTrue(testGroup.getCourses().contains(course1));
    }



    @Test
    void testAddCourseAlreadyThere() {
        checkEmpty();
        testGroup.addCourse(course1);
        assertEquals(testGroup.getCourses().size(), 1);
        assertTrue(testGroup.getCourses().contains(course1));
        testGroup.addCourse(course1);
        assertEquals(testGroup.getCourses().size(), 1);
        assertTrue(testGroup.getCourses().contains(course1));
    }

    @Test
    void testAddMultipleCourses() {
        checkEmpty();
        testGroup.addCourse(course1);
        testGroup.addCourse(course2);
        assertEquals(testGroup.getCourses().size(), 2);
        assertTrue(testGroup.getCourses().contains(course1));
        assertTrue(testGroup.getCourses().contains(course2));
    }

    @Test
    void testRemoveCourse() {
        checkEmpty();
        testGroup.addCourse(course1);
        testGroup.addCourse(course2);
        testGroup.removeCourse(course1.getName());
        ArrayList<Course> courses = testGroup.getCourses();
        assertEquals(courses.size(), 1);
        assertFalse(courses.contains(course1));
        assertTrue(courses.contains(course2));
    }

    @Test
    void testRemovCourseNotExist() {
        checkEmpty();
        testGroup.addCourse(course1);
        testGroup.removeCourse(course2.getName());
        assertEquals(testGroup.getCourses().size(), 1);
        assertTrue(testGroup.getCourses().contains(course1));
    }

    @Test
    void testFindCourseFound() {
        checkEmpty();
        testGroup.addCourse(course1);
        Course found = testGroup.findCourse(course1.getName());
        assertEquals(found.getName(), course1.getName());
    }

    @Test
    void testFindCourseNotFound() {
        checkEmpty();
        testGroup.addCourse(course2);
        Course found = testGroup.findCourse(course1.getName());
        assertNull(found);
    }

    private void checkEmpty() {
        assertEquals(testGroup.getCourses().size(), 0);
        assertFalse(testGroup.getCourses().contains(course1));
    }
}

