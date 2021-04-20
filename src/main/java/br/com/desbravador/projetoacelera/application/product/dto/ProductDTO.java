package br.com.desbravador.projetoacelera.application.product.dto;

import br.com.desbravador.projetoacelera.application.product.entity.Product;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {

    private Long id;
    private String description;
    private String userApp;
    private String password;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.userApp = product.getUserApp();
        this.password = product.getPassword();
    }
}
