CREATE TABLE relatorios (
    id BIGSERIAL,
    formato VARCHAR(50) NOT NULL,
    webhook VARCHAR(500) NOT NULL,
    parametros VARCHAR(5000) NOT NULL,
    id_produto BIGINT NOT NULL,
    id_consumidor BIGINT NOT NULL,
    conteudo VARCHAR(50000) NOT NULL,
    
                  
    PRIMARY KEY(id),
    FOREIGN KEY (id_produto) REFERENCES produtos (id),
    FOREIGN KEY (id_consumidor) REFERENCES consumidores (id)
);