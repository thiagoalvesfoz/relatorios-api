CREATE TABLE products (
    id BIGSERIAL,
    description VARCHAR(250) NOT NULL,
    user_app VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
   
       
    PRIMARY KEY(id)
);