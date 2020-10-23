CREATE TABLE dicionarios (
    id BIGSERIAL,
    tipo VARCHAR(50) NOT NULL,
    nome VARCHAR(250) NOT NULL,
    id_fonte BIGINT NOT NULL,
    id_consumidor BIGINT NOT NULL,
      
                 
    PRIMARY KEY(id),
    FOREIGN KEY (id_fonte) REFERENCES fontededados (id),
    FOREIGN KEY (id_consumidor) REFERENCES consumidores (id)
);