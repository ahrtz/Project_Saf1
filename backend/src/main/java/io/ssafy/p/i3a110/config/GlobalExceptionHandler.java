package io.ssafy.p.i3a110.config;

import org.kohsuke.github.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = NumberFormatException.class)
	public Object handleNumberFormatExceptionForGlobal(NumberFormatException e) {
		System.out.println(e);
		return "NumberFormatException";
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = NullPointerException.class)
	public String handleNullPointerExceptionForGlobal(NullPointerException e) {
		System.out.println(e);
		return "NullPointerException";
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = HttpException.class)
	public String handleHttpExceptionForGlobal(HttpException e) {
		System.out.println(e);
		return "HttpException(Check GitHubId or AccessToken)";
	}
}
