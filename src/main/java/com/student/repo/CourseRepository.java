package com.student.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.model.Courses;

public interface CourseRepository extends JpaRepository<Courses, Long> {
	//Optional<Courses> findByCourseName(String courseName);

}
