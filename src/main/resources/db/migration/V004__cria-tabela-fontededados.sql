CREATE TABLE fontededados (
    id BIGSERIAL,
    caminho VARCHAR(500) NOT NULL,
    url VARCHAR(500) NOT NULL,
    usuario VARCHAR(500) NOT NULL,
    senha VARCHAR(500) NOT NULL,
    token VARCHAR(500) NOT NULL,
              
    PRIMARY KEY(id)
);