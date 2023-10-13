package com.payhub.main.configs;

import com.payhub.infra.errors.*;
import com.payhub.main.properties.ExceptionProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class HandleExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionProperties> handleDefaultException(Exception ex) {
		return new ResponseEntity<>(
			responseFactory(ex, 400),
			HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionProperties> handleNotFoundException(Exception ex) {
		return new ResponseEntity<>(
			responseFactory(ex, 404),
			HttpStatus.NOT_FOUND
		);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionProperties> handleBadRequestException(Exception ex) {
		return new ResponseEntity<>(
			responseFactory(ex, 400),
			HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionProperties> handleSQLException(DataIntegrityViolationException ex) {
		String message = ex.getMessage();
		int start = message.indexOf("[");
		int end = message.indexOf("]");

		var text = message.substring(start + 1, end);
		text = text.substring(0, text.indexOf("for key") - 2);

		var exception = new ExceptionProperties(
			text,
			406,
			LocalDateTime.now()
		);

		return new ResponseEntity<>(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request
	) {
		List<String> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.toList();

		StringBuilder message = new StringBuilder();

		for (int i = 0; i < errors.size(); i++) {
			message.append(errors.get(i));

			if (i != (errors.size() - 1)) {
				message.append(". ");
			} else {
				message.append(".");
			}
		}

		var exception = new ExceptionProperties(
			message.toString(),
			status.value(),
			LocalDateTime.now()
		);

		return new ResponseEntity<>(exception, status);
	}

	private ExceptionProperties responseFactory(Exception ex, int code) {
		return new ExceptionProperties(
			ex.getMessage(),
			code,
			LocalDateTime.now()
		);
	}
}
