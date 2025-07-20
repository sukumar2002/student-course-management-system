package com.student.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.model.Students;

public interface StudentRepository extends JpaRepository<Students, Long> {
	@Query("SELECT s FROM Students s WHERE s.email = :email")
	Optional<Students> findByEmail(@Param("email") String email);
	@Query("SELECT s FROM Students s WHERE s.phno = :phno")
	Optional<Students> findByPhno(@Param("phno") String phno);

}
