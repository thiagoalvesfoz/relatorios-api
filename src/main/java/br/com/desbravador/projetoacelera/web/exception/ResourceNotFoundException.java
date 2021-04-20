package br.com.desbravador.projetoacelera.web.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("The specified resource could not be found by ID");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
