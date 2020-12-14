CREATE TYPE enum_type AS ENUM('API', 'DATABASE');
CREATE TABLE data_sources (
    id BIGSERIAL,
    type enum_type, 
    url VARCHAR(2555) NOT NULL,
    user_app VARCHAR(45) NOT NULL,
    password VARCHAR(2555) NOT NULL,
    token VARCHAR(45),
              
    PRIMARY KEY(id)
);