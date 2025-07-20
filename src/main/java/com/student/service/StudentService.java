package com.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.student.dto.StudentResponseDTO;
import com.student.dto.StudentSaveDTO;
import com.student.response.ResponseMessage;

public interface StudentService {

	public ResponseMessage saveDetails(StudentSaveDTO savedto);

	public List<StudentResponseDTO> getAllStudents();

	public Optional<StudentResponseDTO> getStudentByEmail(String email);

	public Optional<StudentResponseDTO> getStudentById(Long id);

	public ResponseMessage deleteStudent(Long id);

	public ResponseEntity<ResponseMessage> updateStudent(Long id, StudentResponseDTO dto);

	public ResponseEntity<ResponseMessage> partiallyUpdateStudent(Long id, StudentResponseDTO dto);

}
