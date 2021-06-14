package br.com.desbravador.projetoacelera.application.product.dto;

import br.com.desbravador.projetoacelera.application.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductInput {

    private String description;
    private String userApp;
    private String password;

    public Product toModel() {
        var prod = new Product();
        prod.setDescription(description);
        prod.setUserApp(userApp);
        prod.setPassword(password);
        return prod;
    }
}
