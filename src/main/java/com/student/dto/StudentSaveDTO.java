package com.student.dto;

import java.util.Set;

import com.student.model.Courses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSaveDTO {

	@NotBlank(message = "Name is required")
	@NotNull(message = "Name is required")
	@Size(min = 5, message = "Name must be at least 5 characters")
	private String name;

	@NotBlank(message = "Phone number is required")
	@NotNull(message = "Phone Number is not null")
	@Size(min = 10, max = 10, message = "Phone number must be 10 digits")
	private String phno;

	@NotBlank(message = "Email is required")
	@NotNull(message = "Email should not be not null")
	@Email(message = "Email is required")
	private String email;

	@NotBlank(message = "Date is required")
	private String date;

	@NotNull(message = "Course IDs must not be null")
	@Size(min = 1, message = "At least one course ID must be provided")
	private Set<Long> courseIds;
}
