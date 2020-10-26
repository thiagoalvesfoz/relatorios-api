package br.com.desbravador.projetoacelera.web;

public interface DTO<T extends Model> {
	
	T toEntity();
	
}
