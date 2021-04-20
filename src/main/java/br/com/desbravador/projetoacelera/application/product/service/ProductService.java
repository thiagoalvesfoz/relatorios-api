package br.com.desbravador.projetoacelera.application.product.service;

import br.com.desbravador.projetoacelera.application.product.entity.Product;
import br.com.desbravador.projetoacelera.application.product.repository.ProductRepository;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findOne(Long id) {
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
