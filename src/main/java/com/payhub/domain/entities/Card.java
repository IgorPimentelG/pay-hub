package com.payhub.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cards")
public class Card implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String number;

	private String owner;

	private String validity;

	private String cvv;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH, mappedBy = "card")
	private final List<Transaction> transactions;

	public Card() {
		this.transactions = new ArrayList<>();
	}

	public Card(String number, String owner, String validity, String cvv) {
		this.number = number;
		this.owner = owner;
		this.validity = validity;
		this.cvv = cvv;

		this.transactions = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCVV() {
		return cvv;
	}

	public void setCVV(String cvv) {
		this.cvv = cvv;
	}

	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return cvv == card.cvv && Objects.equals(number, card.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number, cvv);
	}
}
