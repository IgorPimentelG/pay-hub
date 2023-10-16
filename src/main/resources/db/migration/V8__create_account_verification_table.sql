CREATE TABLE IF NOT EXISTS account_verifications(
	id CHAR(36) PRIMARY KEY,
	code CHAR(6) NOT NULL,
	expiration DATE NOT NULL,
	is_expired BOOLEAN NOT NULL,
	is_verified BOOLEAN DEFAULT FALSE,

	client_id CHAR(36) NOT NULL,
	CONSTRAINT fk_client_verification FOREIGN KEY (client_id) REFERENCES clients(id)
);