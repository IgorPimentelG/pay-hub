CREATE TABLE IF NOT EXISTS companies(
    id CHAR(36) PRIMARY KEY,
    cnpj CHAR(18) NOT NULL UNIQUE,
    state_registration VARCHAR(15) NOT NULL UNIQUE,
    corporate_name VARCHAR(100) NOT NULL UNIQUE,
    segment VARCHAR(50) NOT NULL,

    address_id CHAR(36) NOT NULL,
    CONSTRAINT address_fk FOREIGN KEY (address_id) REFERENCES addresses (id)
);