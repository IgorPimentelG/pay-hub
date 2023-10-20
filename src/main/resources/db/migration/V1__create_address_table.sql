CREATE TABLE IF NOT EXISTS addresses(
    id CHAR(36) PRIMARY KEY,
    zip_code CHAR(9) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    state CHAR(2) NOT NULL,
    number VARCHAR(5) NOT NULL
);

