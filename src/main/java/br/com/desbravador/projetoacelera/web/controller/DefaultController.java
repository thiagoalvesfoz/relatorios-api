package br.com.desbravador.projetoacelera.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public T save(@RequestBody T entity) {
		return service.save(entity);
	}	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		T entity = service.findOne(id);		
		service.delete(entity);
		return ResponseEntity.noContent().build();
	}
}
