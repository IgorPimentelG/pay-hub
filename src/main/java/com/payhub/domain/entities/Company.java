package com.payhub.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "companies")
public class Company implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String cnpj;

	@Column(name = "state_registration")
	private String stateRegistration;

	@Column(name = "corporate_name")
	private String corporateName;

	private String segment;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToOne(mappedBy = "company")
	private Client client;

	public Company() {}

	public Company(
	  String cnpj,
	  String stateRegistration,
	  String corporateName,
	  String segment,
	  Address address
	) {
		this.cnpj = cnpj;
		this.stateRegistration = stateRegistration;
		this.corporateName = corporateName;
		this.segment = segment;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getStateRegistration() {
		return stateRegistration;
	}

	public void setStateRegistration(String stateRegistration) {
		this.stateRegistration = stateRegistration;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
		Company company = (Company) o;
		return Objects.equals(id, company.id) &&
		  Objects.equals(cnpj, company.cnpj) &&
		  Objects.equals(stateRegistration, company.stateRegistration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, cnpj, stateRegistration);
	}
}
