CREATE TABLE consumidores (
    id BIGSERIAL,
    id_produto BIGINT NOT NULL,
    client_secret VARCHAR(50) NOT NULL,
          
    PRIMARY KEY(id)
);