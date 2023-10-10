package com.payhub.domain.entities;

import com.payhub.domain.types.PaymentMethod;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String description;

	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "card_id")
	private Card card;

	@OneToOne(mappedBy = "transaction")
	private Payable payable;

	public Transaction() {}

	public Transaction(String description, BigDecimal value, Card card, PaymentMethod paymentMethod) {
		this.description = description;
		this.amount = value;
		this.card = card;
		this.paymentMethod = paymentMethod;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Transaction that = (Transaction) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
