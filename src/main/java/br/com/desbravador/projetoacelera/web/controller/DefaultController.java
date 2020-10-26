package br.com.desbravador.projetoacelera.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.desbravador.projetoacelera.web.Model;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

public class DefaultController<T extends Model, S extends DefaultService<T, ?>> {
	
	@Autowired
	protected S service;
	
	@GetMapping
	public ResponseEntity<List<T>> getAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<T> getOne(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findOne(id));
	}	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		T entity = service.findOne(id);		
		service.delete(entity);
		return ResponseEntity.noContent().build();
	}
}
