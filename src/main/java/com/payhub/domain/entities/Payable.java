package com.payhub.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payhub.domain.types.PaymentStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payables")
public class Payable implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_id", referencedColumnName = "id")
	private Transaction transaction;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "client_id")
	private Client client;

	public Payable() {}

	public Payable(Transaction transaction, Client client, LocalDateTime paymentDate, PaymentStatus status) {
		this.transaction = transaction;
		this.client = client;
		this.paymentDate = paymentDate;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Payable payable = (Payable) o;
		return Objects.equals(id, payable.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
