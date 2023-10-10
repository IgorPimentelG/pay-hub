package com.payhub.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String cpf;

	@Column(name = "full_name")
	private String fullName;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")
	private final List<Payable> payables;

	private Client() {
		this.payables = new ArrayList<>();
	}

	public Client(String email, String password, String cpf, String fullName, Company company) {
		this.email = email;
		this.password = password;
		this.cpf = cpf;
		this.fullName = fullName;
		this.company = company;

		this.payables = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void addPayable(Payable payable) {
		payables.add(payable);
	}

	public List<Payable> getPayables() {
		return payables;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Client client = (Client) o;
		return Objects.equals(id, client.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
