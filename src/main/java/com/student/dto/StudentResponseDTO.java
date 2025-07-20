package com.student.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
	 private Long id;
	    private String name;
	    private String phno;
	    private String email;
	    private String date;
	    private Set<CourseResponseDTO> courses;
}
