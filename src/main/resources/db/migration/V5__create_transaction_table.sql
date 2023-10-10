CREATE TABLE IF NOT EXISTS transactions(
    id CHAR(36) PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method ENUM("DEBIT_CARD", "CREDIT_CARD") NOT NULL,

    card_id CHAR(36) NOT NULL,
    CONSTRAINT card_fk FOREIGN KEY (card_id) REFERENCES cards(id)
);