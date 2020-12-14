CREATE TABLE reports_access (
    id BIGSERIAL,
    is_customized boolean,
    consumer_id BIGINT,
    report_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,  
                 
    PRIMARY KEY(id),
    FOREIGN KEY (consumer_id) REFERENCES consumers (id),
    FOREIGN KEY (report_id) REFERENCES reports (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);