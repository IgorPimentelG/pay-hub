CREATE TABLE IF NOT EXISTS payables(
    id CHAR(36) PRIMARY KEY,
    payment_date TIMESTAMP NOT NULL,
    status ENUM("PAID", "WAITING_FUNDS") NOT NULL,

    client_id CHAR(36) NOT NULL,
    CONSTRAINT client_fk FOREIGN KEY (client_id) REFERENCES clients (id),

    transaction_id CHAR(36) NOT NULL,
    CONSTRAINT transaction_fk FOREIGN KEY (transaction_id) REFERENCES transactions(id)
);