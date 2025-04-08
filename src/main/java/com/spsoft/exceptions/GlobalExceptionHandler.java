package com.spsoft.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.spsoft.dto.APIResponse;
import com.spsoft.dto.ResponseMessage;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<APIResponse> handleCustomException(CustomException ex, WebRequest request) {
		ResponseMessage responseMessage = new ResponseMessage(ex.getMessage());
		APIResponse errorDetails = new APIResponse(HttpStatus.BAD_REQUEST.value(), false, responseMessage);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<APIResponse> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
		ResponseMessage responseMessage = new ResponseMessage(ex.getMessage());
		APIResponse errorDetails = new APIResponse(HttpStatus.UNAUTHORIZED.value(), false, responseMessage);
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<APIResponse> handleForbiddenException(ForbiddenException ex, WebRequest request) {
		ResponseMessage responseMessage = new ResponseMessage(ex.getMessage());
		APIResponse errorDetails = new APIResponse(HttpStatus.FORBIDDEN.value(), false, responseMessage);
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<APIResponse> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
		ResponseMessage responseMessage = new ResponseMessage(ex.getMessage());
		APIResponse errorDetails = new APIResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), false, responseMessage);
		return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIResponse> handleGlobalException(Exception ex, WebRequest request) {
		ResponseMessage responseMessage = new ResponseMessage("Internal Server Error: " + ex.getMessage());
		APIResponse errorDetails = new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, responseMessage);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}