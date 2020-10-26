package br.com.desbravador.projetoacelera.web.exceptionhandler;

import java.time.Instant;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		var httpStatus = status.value();
		
		var timestamp = Instant.now();
		
		var title = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
		
		var path = request.getDescription(false).substring(4);
		
		var body = new ResponseApi(httpStatus, timestamp, title, path);
		
		var fields = new ArrayList<Field>();
		
		for (ObjectError erro: ex.getBindingResult().getAllErrors()) {
			var name = ((FieldError)erro).getField();
			
			var message = erro.getDefaultMessage();
			
			fields.add(new Field(name, message));
		}
		
		body.setFields(fields);
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseApi> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
		
		var status = HttpStatus.NOT_FOUND.value();
		
		var path = request.getRequestURI();
		
		var timestamp = Instant.now();
		
		var erro = new ResponseApi(status, timestamp, ex.getMessage(), path);
		
		return ResponseEntity.status(status).body(erro);
	}
}
