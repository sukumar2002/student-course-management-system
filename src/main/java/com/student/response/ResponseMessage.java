package com.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

	private String message;
	private boolean status;
	private int code;
	private Object data;

	 public ResponseMessage(String message, boolean status, int code) {
	        this.message = message;
	        this.status = status;
	        this.code = code;
	        this.data = "No student created";  
	    }
}
