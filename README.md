# Student-Course Management System

The Student-Course Management System is a Spring Boot-based RESTful API project that allows the creation and management of students and their course enrollments. It demonstrates a many-to-many relationship between students and courses using Java, Spring Data JPA, and MySQL.

---

## Features

- Add, update, delete, and fetch student details
- Add, update, delete, and fetch course details
- Assign multiple courses to a student
- Retrieve all students and courses
- Implements DTOs and service layers
- Validates input fields
- Uses MySQL as the database

---

## Technologies Used

- Java 17  
- Spring Boot  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Maven  
- Postman (for API testing)

---

## Project Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/sukumar2002/student-course-management-system.git
Navigate to the Project Directory:

bash
Copy
Edit
cd student-course-management-system
Open in IntelliJ IDEA or any IDE of your choice

Configure MySQL Database in application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Run the Application:

bash
Copy
Edit
mvn spring-boot:run
API Endpoints (Tested with Postman)
Student Endpoints
➤ Save a Student
http
Copy
Edit
POST /student/save
Request Body:

json
Copy
Edit
{
  "name": "raju",
  "phno": "2342543327",
  "email": "raju@example.com",
  "courseid": [1, 3],
  "date": "2023-02-12"
}
➤ Get All Students
http
Copy
Edit
GET /student/all
➤ Get Student by ID
http
Copy
Edit
GET /student/{id}
➤ Update Student
http
Copy
Edit
PUT /student/update/{id}
Request Body:

json
Copy
Edit
{
  "name": "updated name",
  "phno": "9999999999",
  "email": "updated@example.com",
  "courseid": [2],
  "date": "2024-01-01"
}
➤ Delete Student
http
Copy
Edit
DELETE /student/delete/{id}
Course Endpoints
➤ Save a Course
http
Copy
Edit
POST /course/save
Request Body:

json
Copy
Edit
{
  "cname": "Java",
  "duration": "3 Months"
}
➤ Get All Courses
http
Copy
Edit
GET /course/all
➤ Get Course by ID
http
Copy
Edit
GET /course/{id}
➤ Update Course
http
Copy
Edit
PUT /course/update/{id}
Request Body:

json
Copy
Edit
{
  "cname": "Spring Boot",
  "duration": "2 Months"
}
➤ Delete Course
http
Copy
Edit
DELETE /course/delete/{id}
Folder Structure
pgsql
Copy
Edit
student-course-management-system/
├── src/
│   └── main/
│       ├── java/
│       │   └── com.student/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── exception/
│       │       ├── model/
│       │       ├── repository/
│       │       ├── service/
│       │       └── serviceimpl/
│       └── resources/
│           ├── application.properties
│           └── data.sql
├── pom.xml
└── README.md
Response Structure
Most endpoints return a response like:

json
Copy
Edit
{
  "message": "Student saved successfully",
  "status": true,
  "code": 200
}
Notes
The courseid in the student request body should be an array of existing course IDs.

Input validations are done using @NotNull, @NotBlank, and @Email annotations.

Error handling is implemented using custom exception classes.
