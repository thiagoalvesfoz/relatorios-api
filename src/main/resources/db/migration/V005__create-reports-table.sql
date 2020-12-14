CREATE TABLE reports (
    id BIGSERIAL,
    name VARCHAR(60) NOT NULL,
    format VARCHAR(45) NOT NULL,
    webhook VARCHAR(500) NOT NULL,
    content VARCHAR(5000) NOT NULL,
      
                  
    PRIMARY KEY(id)
);