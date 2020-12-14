CREATE TABLE db_columns (
    id BIGSERIAL,
    type VARCHAR(45) NOT NULL,
    description VARCHAR(60),
    name VARCHAR(60) NOT NULL,
    table_id BIGINT NOT NULL,
     
                 
    PRIMARY KEY(id),
    FOREIGN KEY (table_id) REFERENCES db_tables (id)
);