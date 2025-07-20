package com.student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.dto.StudentResponseDTO;
import com.student.dto.StudentSaveDTO;
import com.student.response.ResponseMessage;
import com.student.service.StudentService;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> saveStudent(@Validated @RequestBody StudentSaveDTO dto) {
        return new ResponseEntity<>(studentService.saveDetails(dto), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<ResponseMessage> getStudentByMailId(@PathVariable String mail) {
        Optional<StudentResponseDTO> studentByEmail = studentService.getStudentByEmail(mail);
        if (studentByEmail.isPresent()) {
            ResponseMessage response = new ResponseMessage("Student found", true, 200, studentByEmail.get());
            return ResponseEntity.ok(response);
        } else {
            ResponseMessage response = new ResponseMessage("Student not found", false, 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseMessage> getStudentByID(@PathVariable Long id) {
        Optional<StudentResponseDTO> studentById = studentService.getStudentById(id);
        if (studentById.isPresent()) {
            ResponseMessage response = new ResponseMessage("Student found", true, 200, studentById.get());
            return ResponseEntity.ok(response);
        } else {
            ResponseMessage response = new ResponseMessage("Student not found", false, 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateStudent(@PathVariable Long id, @RequestBody StudentResponseDTO studentDTO) {
        return studentService.updateStudent(id, studentDTO);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseMessage> partialUpdateStudent(@PathVariable Long id, @RequestBody StudentResponseDTO dto) {
        return studentService.partiallyUpdateStudent(id, dto);
    }


} 
