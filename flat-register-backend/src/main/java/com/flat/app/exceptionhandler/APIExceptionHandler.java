package com.flat.app.exceptionhandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flat.app.exception.FlatNotFoundException;
import com.flat.app.exception.FlatRegisteredException;
import com.flat.app.exception.NoFlatsFoundException;
import com.flat.app.exception.NoUsersFoundException;

@RestControllerAdvice
public class APIExceptionHandler {

	@ExceptionHandler(FlatNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleFlatNotFound(FlatNotFoundException exception) {
		Map<String, Object> errorMap = new LinkedHashMap<>();

		errorMap.put("statusCode", 500);
		errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		errorMap.put("timeStamp", LocalDateTime.now());
		errorMap.put("message", exception.getMessage());

		return errorMap;
	}

	@ExceptionHandler(NoFlatsFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleNoFlatsFound(NoFlatsFoundException exception) {
		Map<String, Object> errorMap = new LinkedHashMap<>();

		errorMap.put("statusCode", 500);
		errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		errorMap.put("timeStamp", LocalDateTime.now());
		errorMap.put("message", exception.getMessage());

		return errorMap;
	}

	@ExceptionHandler(NoUsersFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleNoOwnersFound(NoUsersFoundException exception) {
		Map<String, Object> errorMap = new LinkedHashMap<>();

		errorMap.put("statusCode", 500);
		errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		errorMap.put("timeStamp", LocalDateTime.now());
		errorMap.put("message", exception.getMessage());

		return errorMap;
	}

	@ExceptionHandler(FlatRegisteredException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleFlatRegistered(FlatRegisteredException exception) {
		Map<String, Object> errorMap = new LinkedHashMap<>();

		errorMap.put("statusCode", 500);
		errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		errorMap.put("timeStamp", LocalDateTime.now());
		errorMap.put("message", exception.getMessage());

		return errorMap;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleInvalidArgument(MethodArgumentNotValidException exception) {
		Map<String, Object> errorMap = new LinkedHashMap<>();

		errorMap.put("statusCode", 400);
		errorMap.put("status", HttpStatus.BAD_REQUEST);
		errorMap.put("timeStamp", LocalDateTime.now());

		exception.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});

		return errorMap;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
		Map<String, Object> errorMap = new LinkedHashMap<>();

		errorMap.put("statusCode", 500);
		errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		errorMap.put("timeStamp", LocalDateTime.now());
		errorMap.put("message", "Some information of owner provided duplicate");

		return errorMap;
	}

}
