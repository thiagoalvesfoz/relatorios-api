CREATE TABLE usuarios (
    id BIGSERIAL,
    nome VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    senha VARCHAR(250) NOT NULL,
    token VARCHAR(250),
    
    administrador BOOLEAN DEFAULT false,
    bloqueado BOOLEAN DEFAULT false,
    ativo BOOLEAN DEFAULT true,
    
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    PRIMARY KEY(id)
);