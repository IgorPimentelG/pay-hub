package com.payhub.infra.services;

import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mocks.AddressMock;
import com.payhub.infra.mocks.ClientMock;
import com.payhub.infra.mocks.CompanyMock;
import com.payhub.infra.repositories.AddressRepository;
import com.payhub.infra.repositories.CompanyRepository;
import com.payhub.main.configs.secutiry.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompanyServiceTests {

	@InjectMocks
	CompanyService service;

	@Mock
	AddressService addressService;

	@Mock
	ClientService clientService;

	@Mock
	CompanyRepository repository;

	@Mock
	AddressRepository addressRepository;

	@Mock
	SecurityContext securityContext;


	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);

		var client = ClientMock.createEntity();
		when(securityContext.currentUser()).thenReturn(client);
		when(clientService.findById(any())).thenReturn(client);
	}

	@Test
	@DisplayName("should create a company")
	void testCreateCompany() {
		var companyEntity = CompanyMock.createEntity();
		var companyDto = CompanyMock.createDto();
		var addressEntity = AddressMock.createEntity();

		when(addressRepository.save(any())).thenReturn(addressEntity);
		when(repository.save(any())).thenReturn(companyEntity);
		when(addressService.create(any())).thenReturn(addressEntity);

		var result = service.create(companyDto);

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(result.getCnpj(), companyDto.cnpj());
		assertEquals(result.getSegment(), companyDto.segment());
		assertEquals(result.getCorporateName(), companyDto.corporateName());
		assertEquals(result.getStateRegistration(), companyDto.stateRegistration());
	}

	@Test
	@DisplayName("should throws exception when creating company with data null")
	void testErrorThrownOnCreatingCompanyWithNullData() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "It is necessary to provide company details.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should update a company")
	void testUpdateCompany() {
		var companyEntity = CompanyMock.createEntity();
		var companyDto = CompanyMock.updateDto();

		var client = ClientMock.createEntity();
		client.setCompany(companyEntity);
		when(securityContext.currentUser()).thenReturn(client);
		when(clientService.findById(any())).thenReturn(client);
		when(repository.findById(any())).thenReturn(Optional.of(companyEntity));
		when(repository.save(any())).thenReturn(companyEntity);

		var result = service.update(companyDto);

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(result.getSegment(), companyDto.segment());
		assertEquals(result.getCorporateName(), companyDto.corporateName());
	}

	@Test
	@DisplayName("should throws exception when update a company with data null")
	void testErrorThrownOnUpdatingCompanyWithNullData() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.update(null);
		});

		String expectedMessage = "It is necessary to provide company details.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws exception when update a company doesn't exist")
	void testErrorThrownOnUpdatingAddressDoesNotExist() {
		var companyUpdateDto = CompanyMock.updateDto();

		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.update(companyUpdateDto);
		});

		String expectedMessage = "You don't have a registered company.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should find a company by id")
	void testFindCompanyById() {
		var companyEntity = CompanyMock.createEntity();

		when(repository.findById(any())).thenReturn(Optional.of(companyEntity));

		var result = service.findById("any id");

		verify(repository, times(1)).findById(any());
		assertNotNull(result);
		assertEquals(result.getCnpj(), companyEntity.getCnpj());
		assertEquals(result.getSegment(), companyEntity.getSegment());
		assertEquals(result.getCorporateName(), companyEntity.getCorporateName());
		assertEquals(result.getStateRegistration(), companyEntity.getStateRegistration());
	}

	@Test
	@DisplayName("should throws exception when find a company by id doesn't exist")
	void testErrorThrownOnFindCompanyByIdDoesNotExist() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.findById("any id");
		});

		String expectedMessage = "The company with id: any id is not registered.";
		String resultMessage = exception.getMessage();

		verify(repository, times(1)).findById(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should find a company by CNPJ")
	void testFindCompanyByCNPJ() {
		var companyEntity = CompanyMock.createEntity();

		when(repository.findByCnpj(any())).thenReturn(Optional.of(companyEntity));

		var result = service.findByCNPJ("any cnpj");

		verify(repository, times(1)).findByCnpj(any());
		assertNotNull(result);
		assertEquals(result.getCnpj(), companyEntity.getCnpj());
		assertEquals(result.getSegment(), companyEntity.getSegment());
		assertEquals(result.getCorporateName(), companyEntity.getCorporateName());
		assertEquals(result.getStateRegistration(), companyEntity.getStateRegistration());
	}

	@Test
	@DisplayName("should throws exception when find a company by cnpj doesn't exist")
	void testErrorThrownOnFindCompanyByCNPJDoesNotExist() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.findByCNPJ("any cnpj");
		});

		String expectedMessage = "The company with CNPJ: any cnpj is not registered.";
		String resultMessage = exception.getMessage();

		verify(repository, times(1)).findByCnpj(any());
		assertEquals(expectedMessage, resultMessage);
	}
}
