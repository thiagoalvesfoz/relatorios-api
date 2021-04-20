package br.com.desbravador.projetoacelera.application.product.entity;

import lombok.Data;
import javax.persistence.*;


@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "user_app")
    private String userApp;
    private String password;

}
