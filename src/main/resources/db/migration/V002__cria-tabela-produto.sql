CREATE TABLE produtos (
    id BIGSERIAL,
    descricao VARCHAR(250) NOT NULL,
    username VARCHAR(50) NOT NULL,
    senha VARCHAR(50) NOT NULL,
   
       
    PRIMARY KEY(id)
);