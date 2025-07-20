package com.student.exception;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.student.response.ResponseMessage;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResponseMessage> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String name = ex.getName();
		String type = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";
		String value = ex.getValue() != null ? ex.getValue().toString() : "null";

		String message = "Invalid value '" + value + "' for parameter '" + name + "'. Expected type: " + type;

		return ResponseEntity.badRequest().body(new ResponseMessage(message, false, 400, null));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("message", ex.getMessage());
		errorBody.put("status", false);
		errorBody.put("code", HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseMessage> handleValidationException(MethodArgumentNotValidException ex) {
		String errorMsg = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).findFirst()
				.orElse("Validation failed");

		return ResponseEntity.badRequest().body(new ResponseMessage(errorMsg, false, 400, null));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseMessage> handleConstraintViolation(ConstraintViolationException ex) {
		String message = ex.getConstraintViolations().stream().map(cv -> cv.getMessage()).findFirst()
				.orElse("Constraint violation");

		return ResponseEntity.badRequest().body(new ResponseMessage(message, false, 400, null));
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ResponseMessage> handleStudentNotFound(StudentNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(ex.getMessage(), false, 404, null));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseMessage> handleAllOtherExceptions(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseMessage("Internal Server Error: " + ex.getMessage(), false, 500, null));
	}
	
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<ResponseMessage> handleDateParseException(DateTimeParseException ex) {
	    return ResponseEntity.badRequest().body(
	        new ResponseMessage("Invalid date format. Please use 'yyyy-MM-dd'.", false, 400, null)
	    );
	}

}
