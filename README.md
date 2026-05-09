# Student Course Enrollment System

A JavaFX desktop application for managing student course enrollment records using MySQL and JDBC.

## Overview📌

This project allows users to manage course enrollments for students.

The system supports:

- Add Enrollment
- Update Enrollment
- Delete Enrollment
- View All Enrollments
- Prevent duplicate enrollment for the same student in the same course

## Technologies Used⚙️

- Java
- JavaFX
- FXML
- CSS
- JDBC
- MySQL

## Project Structure🗂️ 

```text
src/
├── app/
│   └── Main.java
├── config/
│   └── DBConnection.java
├── controllers/
│   └── EnrollmentController.java
├── dao/
│   ├── StudentDAO.java
│   ├── CourseDAO.java
│   └── EnrollmentDAO.java
├── models/
│   ├── Student.java
│   ├── Course.java
│   └── Enrollment.java
├── views/
│   └── Enrollment.fxml
└── styles/
    └── EnrollmentStyle.css
```

## Database Tables

The project uses three tables:

- `students`
- `courses`
- `enrollment`

The `enrollment` table connects students with courses.

## How to Run

1. Create the MySQL database.
2. Import the SQL tables and sample data.
3. Update the database connection in `DBConnection.java`.
4. Add the MySQL JDBC Driver to the project.
5. Add JavaFX libraries to the project.
6. Run `Main.java`.


## Author👩🏻‍💻

Created by Waed rabah zaqout
