package br.com.desbravador.projetoacelera.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desbravador.projetoacelera.application.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
