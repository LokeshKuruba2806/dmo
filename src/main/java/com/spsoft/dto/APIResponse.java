package com.spsoft.dto;

import lombok.Data;

@Data
public class APIResponse {
	
	private Integer statusCode;
	private Boolean isError;
	private Object result;
	public APIResponse() {
		super();
	}
	public APIResponse(Integer statusCode, Boolean isError, Object result) {
		super();
		this.statusCode = statusCode;
		this.isError = isError;
		this.result = result;
	}
	

}
