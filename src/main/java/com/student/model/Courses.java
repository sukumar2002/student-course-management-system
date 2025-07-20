package com.student.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courses {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;
	
	@NotBlank(message="Course is required")
	@NotNull(message="It should not be blank")
	@Column(name="Course_Name")
	private String courseName;
	
	@ManyToMany(mappedBy = "courses")
	private Set<Students> students;

}
