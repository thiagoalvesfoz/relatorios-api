CREATE TABLE usuarios (
    id BIGSERIAL,
    nome VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    senha VARCHAR(250) NOT NULL,
    token VARCHAR(250) NOT NULL,
    
    administrador BOOLEAN DEFAULT false,
    bloqueado BOOLEAN DEFAULT false,
    ativo BOOLEAN DEFAULT true,
    
    criado_em DATE DEFAULT now(),
    atualizado_em DATE DEFAULT now(),
    
    PRIMARY KEY(id)
);