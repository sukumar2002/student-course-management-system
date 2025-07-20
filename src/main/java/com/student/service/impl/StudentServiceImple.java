package com.student.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.converter.StudentConverter;
import com.student.dto.CourseResponseDTO;
import com.student.dto.StudentResponseDTO;
import com.student.dto.StudentSaveDTO;
import com.student.model.Courses;
import com.student.model.Students;
import com.student.repo.CourseRepository;
import com.student.repo.StudentRepository;
import com.student.response.ResponseMessage;
import com.student.service.StudentService;

@Service
public class StudentServiceImple implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentConverter studentConverter;

	@Override
	public ResponseMessage saveDetails(StudentSaveDTO dto) {
		if (dto.getName() == null || dto.getName().isBlank()) {
			return new ResponseMessage("Name is required", false, 400, null);
		}
		if (dto.getPhno() == null || dto.getPhno().isBlank()) {
			return new ResponseMessage("Phone number is required", false, 400, null);
		}
		if (dto.getPhno().trim().length() != 10) {
			return new ResponseMessage("Phone number must be 10 digits", false, 400, null);
		}
		if (dto.getEmail() == null || dto.getEmail().isBlank()) {
			return new ResponseMessage("Email is required", false, 400, null);
		}
		if (dto.getDate() == null || dto.getDate().isBlank()) {
			return new ResponseMessage("Date is required", false, 400, null);
		}

		if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
			return new ResponseMessage("Email already exists", false, 400, null);
		}

		if (studentRepository.findByPhno(dto.getPhno()).isPresent()) {
			return new ResponseMessage("Phone number already exists", false, 400, null);
		}
		if (dto.getCourseIds() == null || dto.getCourseIds().isEmpty()) {
			throw new IllegalArgumentException("Please select at least one course");
		}

		Students student = new Students();
		student.setName(dto.getName());
		student.setPhno(dto.getPhno());
		student.setEmail(dto.getEmail());
		student.setDate(LocalDate.parse(dto.getDate()));

		Set<Courses> courseSet = new HashSet<>();
		for (Long id : dto.getCourseIds()) {
			Optional<Courses> courseOpt = courseRepository.findById(id);
			if (courseOpt.isEmpty()) {
				return new ResponseMessage("Course not found with ID: " + id, false, 404, null);
			}
			courseSet.add(courseOpt.get());
		}

		student.setCourses(courseSet);
		studentRepository.save(student);

		StudentResponseDTO responseDTO = new StudentResponseDTO();
		responseDTO.setId(student.getId());
		responseDTO.setName(student.getName());
		responseDTO.setPhno(student.getPhno());
		responseDTO.setEmail(student.getEmail());
		responseDTO.setDate(student.getDate().toString());
		responseDTO.setCourses(
				courseSet.stream().map(course -> new CourseResponseDTO(course.getCourseId(), course.getCourseName()))
						.collect(Collectors.toSet()));

		return new ResponseMessage("Student saved successfully", true, 200, responseDTO);
	}

	@Override
	public Optional<StudentResponseDTO> getStudentByEmail(String email) {
		return studentRepository.findByEmail(email).map(student -> {
			StudentResponseDTO dto = new StudentResponseDTO();
			dto.setId(student.getId());
			dto.setName(student.getName());
			dto.setEmail(student.getEmail());
			dto.setPhno(student.getPhno());
			dto.setDate(student.getDate().toString());
			dto.setCourses(student.getCourses().stream().map(c -> new CourseResponseDTO(c.getCourseId(), c.getCourseName()))
					.collect(Collectors.toSet()));
			return dto;
		});
	}

	@Override
	public Optional<StudentResponseDTO> getStudentById(Long id) {
	    return studentRepository.findById(id)
	            .map(StudentConverter::entityToDto);
	}


	@Override
	public ResponseMessage deleteStudent(Long id) {
		if (studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
			return new ResponseMessage("Deleted successfully", true, 200, null);
		} else {
			return new ResponseMessage("Student not found", false, 404, null);
		}
	}

	@Override
	public List<StudentResponseDTO> getAllStudents() {
		List<Students> students = studentRepository.findAll();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		return students.stream().map(student -> {
			StudentResponseDTO dto = new StudentResponseDTO();
			dto.setId(student.getId());
			dto.setName(student.getName());
			dto.setPhno(student.getPhno());
			dto.setEmail(student.getEmail());
			dto.setDate(student.getDate().format(formatter));

			Set<CourseResponseDTO> courseDTOs = student.getCourses().stream()
				    .map(course -> new CourseResponseDTO(course.getCourseId(), course.getCourseName()))
				    .collect(Collectors.toSet());

				dto.setCourses(courseDTOs);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<ResponseMessage> updateStudent(Long id, StudentResponseDTO studentDTO) {
	    Students student = studentRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Student not found"));

	    // Update student fields
	    student.setName(studentDTO.getName());
	    student.setEmail(studentDTO.getEmail());
	    student.setPhno(studentDTO.getPhno());
	    student.setDate(LocalDate.parse(studentDTO.getDate()));

  
	    Set<Long> courseIds = studentDTO.getCourses().stream().map(courseDTO->courseDTO.getCourseId())
	    .collect(Collectors.toSet());
	    Set<Courses> courseEntities = new HashSet<>(courseRepository.findAllById(courseIds));
	    student.setCourses(courseEntities);

	   
	    Students updatedStudent = studentRepository.save(student);

	   
	    StudentResponseDTO responseDTO = StudentConverter.entityToDto(updatedStudent);

	    ResponseMessage message = new ResponseMessage(
	        "Student updated successfully",
	        true,
	        200,
	        responseDTO
	    );

	    return ResponseEntity.ok(message);
	}

	@Override
	public ResponseEntity<ResponseMessage> partiallyUpdateStudent(Long id, StudentResponseDTO dto) {
	    Students student = studentRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

	    if (dto.getName() != null) student.setName(dto.getName());
	    if (dto.getPhno() != null) student.setPhno(dto.getPhno());
	    if (dto.getEmail() != null) student.setEmail(dto.getEmail());
	    if (dto.getDate() != null) student.setDate(LocalDate.parse(dto.getDate()));
	    if (dto.getCourses() != null) {
	        Set<Courses> courses = dto.getCourses().stream()
	            .map(courseDTO -> {
	                Courses course = new Courses();
	                course.setCourseName(courseDTO.getCourseName());
	                return course;
	            }).collect(Collectors.toSet());
	        student.setCourses(courses);
	    }

	    Students updatedStudent = studentRepository.save(student);

	    StudentResponseDTO updatedDto = StudentConverter.entityToDto(updatedStudent);
	    ResponseMessage response = new ResponseMessage("Student updated successfully", true, 200, updatedDto);

	    return ResponseEntity.ok(response);
	}


	

}
