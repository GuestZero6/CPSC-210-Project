package model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTracker {
    private TimeTracker tracker;
    private CourseGroup group1;
    private CourseGroup group2;

    @BeforeEach
    void runBefore() {
        tracker = new TimeTracker();
        group1 = new CourseGroup("Term 1");
        group2 = new CourseGroup("Term 2");
    }

    @Test
    void testConstructor() {
        assertEquals(tracker.getGroups().size(), 0);
    }

    @Test
    void testAddGroup() {
        checkEmpty();
        tracker.addGroup(group1);
        ArrayList<CourseGroup> groups = tracker.getGroups();
        assertEquals(1, groups.size());
        assertTrue(groups.contains(group1));
    }

    @Test
    void testAddGroupAlreadyThere() {
        checkEmpty();
        tracker.addGroup(group1);
        ArrayList<CourseGroup> groups = tracker.getGroups();
        assertEquals(1, groups.size());
        assertTrue(groups.contains(group1));
        tracker.addGroup(group1);
        assertEquals(1, groups.size());
        assertTrue(groups.contains(group1));
    }

    @Test
    void testAddMultipleGroups() {
        checkEmpty();
        tracker.addGroup(group1);
        tracker.addGroup(group2);
        ArrayList<CourseGroup> groups = tracker.getGroups();
        assertEquals(2, groups.size());
        assertTrue(groups.contains(group1));
        assertTrue(groups.contains(group2));
    }

    @Test
    void testRemoveGroup() {
        checkEmpty();
        tracker.addGroup(group1);
        tracker.addGroup(group2);
        tracker.removeGroup(group1.getGroupName());
        ArrayList<CourseGroup> groups = tracker.getGroups();
        assertEquals(1, groups.size());
        assertFalse(groups.contains(group1));
        assertTrue(groups.contains(group2));
    }

    @Test
    void testRemoveGroupNotExist() {
        checkEmpty();
        tracker.addGroup(group1);
        tracker.removeGroup(group2.getGroupName());
        ArrayList<CourseGroup> groups = tracker.getGroups();
        assertEquals(1, groups.size());
        assertTrue(groups.contains(group1));
    }

    @Test
    void testFindGroupFound() {
        checkEmpty();
        tracker.addGroup(group1);
        CourseGroup found = tracker.findGroup(group1.getGroupName());
        assertEquals(found.getGroupName(), group1.getGroupName());
    }

    @Test
    void testFindGroupNotFound() {
        checkEmpty();
        tracker.addGroup(group2);
        CourseGroup found = tracker.findGroup(group1.getGroupName());
        assertNull(found);
    }

    private void checkEmpty() {
        assertEquals(tracker.getGroups().size(), 0);
        assertFalse(tracker.getGroups().contains(group1));
    }
}
