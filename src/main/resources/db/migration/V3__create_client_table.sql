CREATE TABLE IF NOT EXISTS clients(
    id CHAR(36) PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    cpf CHAR(14) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,

    company_id CHAR(36) NOT NULL,
    CONSTRAINT company_fk FOREIGN KEY (company_id) REFERENCES companies (id)
);