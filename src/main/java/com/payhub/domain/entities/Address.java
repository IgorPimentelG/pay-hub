package com.payhub.domain.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String zipCode;

	private String street;

	private String city;

	private String neighborhood;

	private String state;

	private String number;

	@OneToOne(mappedBy = "address")
	private Company company;

	public Address() {}

	public Address(
	  String zipCode,
	  String street,
	  String city,
	  String neighborhood,
	  String state,
	  String number
	) {
		this.zipCode = zipCode;
		this.street = street;
		this.city = city;
		this.neighborhood = neighborhood;
		this.state = state;
		this.number = number;
	}

	public String getId() {
		return id;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(id, address.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
