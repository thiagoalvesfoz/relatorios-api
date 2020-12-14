CREATE TABLE report_filters (
    id BIGSERIAL,
    type VARCHAR(45) NOT NULL,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(60),
    report_id BIGINT NOT NULL,
     
                 
    PRIMARY KEY(id),
    FOREIGN KEY (report_id) REFERENCES reports (id)
);