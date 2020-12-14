CREATE TABLE db_tables (
    id BIGSERIAL,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(255) NOT NULL,
    data_source_id BIGINT NOT NULL,
    report_id BIGINT NOT NULL,
      
                 
    PRIMARY KEY(id),
    FOREIGN KEY (data_source_id) REFERENCES data_sources (id),
    FOREIGN KEY (report_id) REFERENCES reports (id)
);