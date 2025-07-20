package com.student.converter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.student.dto.CourseResponseDTO;
import com.student.dto.StudentResponseDTO;
import com.student.model.Courses;
import com.student.model.Students;

@Component
public class StudentConverter {

	public static Students dtoToEntity(StudentResponseDTO dto) {
		Students student = new Students();
		student.setId(dto.getId());
		student.setName(dto.getName());
		student.setPhno(dto.getPhno());
		student.setEmail(dto.getEmail());

		if (dto.getDate() != null) {
			student.setDate(LocalDate.parse(dto.getDate()));
		}

		if (dto.getCourses() != null && !dto.getCourses().isEmpty()) {
			Set<Courses> courseEntities = dto.getCourses().stream().map(courseDto -> {
				Courses course = new Courses();
				course.setCourseId(courseDto.getCourseId()); // Important if you're updating or reusing
				course.setCourseName(courseDto.getCourseName());
				return course;
			}).collect(Collectors.toSet());
			student.setCourses(courseEntities);
		}

		return student;
	}

	public static StudentResponseDTO entityToDto(Students student) {
		StudentResponseDTO dto = new StudentResponseDTO();
		dto.setId(student.getId());
		dto.setName(student.getName());
		dto.setEmail(student.getEmail());
		dto.setPhno(student.getPhno());
		dto.setDate(student.getDate().toString());

		Set<CourseResponseDTO> courseDTOs = Optional.ofNullable(student.getCourses()).orElse(Collections.emptySet())
				.stream().map(course -> new CourseResponseDTO(course.getCourseId(), course.getCourseName()))
				.collect(Collectors.toSet());

		dto.setCourses(courseDTOs);
		return dto;
	}

}
