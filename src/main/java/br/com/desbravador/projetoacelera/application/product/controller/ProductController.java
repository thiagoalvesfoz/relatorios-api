package br.com.desbravador.projetoacelera.application.product.controller;

import br.com.desbravador.projetoacelera.application.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.desbravador.projetoacelera.application.product.entity.Product;
import br.com.desbravador.projetoacelera.application.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findAll() {
        log.info("Requesting all products");
        return productService.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ProductDTO findOneProduct(@PathVariable Long id) {
        log.info("Requesting product with id {}", id);
        return new ProductDTO(productService.findOne(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO save(@RequestBody Product product) {
        log.info("Requesting the creation of a product: {}", product);
        return new ProductDTO(productService.save(product));
    }

}
