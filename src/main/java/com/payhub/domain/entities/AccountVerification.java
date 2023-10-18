package com.payhub.domain.entities;

import com.payhub.domain.types.VerificationMethod;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "account_verifications")
public class AccountVerification implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String code;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "client_id")
	private Client client;

	private LocalDateTime expiration;

	@Column(name = "is_expired")
	private boolean isExpired;

	@Column(name = "is_verified")
	private boolean isVerified;

	@Column(name = "verification_method")
	@Enumerated(EnumType.STRING)
	private VerificationMethod method;

	public AccountVerification() {
		init();
	}

	public AccountVerification(
		String code,
		Client client,
		LocalDateTime expiration,
		VerificationMethod method
	) {
		this.code = code;
		this.client = client;
		this.expiration = expiration;
		this.method = method;

		init();
	}

	private void init() {
		isExpired = false;
		isVerified = false;
	}

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean expired) {
		isExpired = expired;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public VerificationMethod getMethod() {
		return method;
	}

	public void setMethod(VerificationMethod method) {
		this.method = method;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AccountVerification that = (AccountVerification) o;
		return Objects.equals(id, that.id) && Objects.equals(code, that.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code);
	}
}
