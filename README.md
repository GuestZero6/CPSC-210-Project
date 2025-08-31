# Study Time Tracker
Study Time Tracker is a Java application that is designed for students who want to keep track of the hours they have spent studying for each course in the semester. The application allows users to create multiple groups of courses for different study periods. Within each group, users can add courses, update study hours and view total hours studied.

## Application Background
As a university student, I often find it hard to balance between different courses. Sometimes I would focus too much on one course and not enough on the others. The Study Time Tracker will help students like me manage our learning more effectively.

## User Stories

- As a user, I want to create a new course group.
- As a user, I want to add a course to a specific group.
- As a user, I want to view all the courses in the groups.
- As a user, I want to update the total study hours for a course every day.
- As a user, I want to remove a course from a group.
- As a user, I want to be able to save my course groups and study hours.
- As a user, I want to be able to load my course groups and study hours.

## Instructions for End User

- You can create a new course group by clicking the "Create New Course Group" button and typing in the group name.
- You can add a course to a specific group by clicking the "Add Course to Group" button, typing in the group name, and then typing in the course you want to add.
- You can remove a course from a group by clicking the "Remove Course from Group" button, typing in the group name, and then typing in the course you want to remove.
- You can view all the courses in the groups by clicking the "View all Courses" button.
- You can update the total study hours for a course by clicking the "Update Study Hours" button, typing in the group name, and then typing in the course you want to update.
- You can save the state of the timetracker by clicking the "Save" button.
- You can reload the state of the timetracker by clicking the "Load" button.
- You can clear all displays by clicking the "Clear All Dispalys" button.

## Phase 4: Task 2
Tue Aug 05 22:41:33 PDT 2025 <br>
Course group '1' added to time tracker.

Tue Aug 05 22:41:38 PDT 2025<br>
Course '1' added to group '1'.

Tue Aug 05 22:42:15 PDT 2025<br>
10.0 hours added to course '1'.

Tue Aug 05 22:42:30 PDT 2025<br>
Course '1' removed from group '1'.

## Phase 4: Task 3
If I had more time to work on this project, I would refactor the buttons in the GUI. Right now, all the code for the function of each button is written directly inside the `TimeTrackerAppGUI` class. This makes the class a bit repetitive and messy. Therefore to improve the cohesion, I would create an abstract class called `Function`, and then have each button action like `CreateGroup` be its own class that extends `Function`. This way, I can implement the methods in each subclass which improves readibility. Moreover, it would be much easier to add new buttons in the future.
