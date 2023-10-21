package com.payhub.infra.mocks;

import com.payhub.domain.entities.Company;
import com.payhub.infra.dtos.client.CreateCompanyDto;
import com.payhub.infra.dtos.client.UpdateCompanyDto;

public abstract class CompanyMock {

		public static Company createEntity() {
			var company = new Company(
				"00.000.000/0000-00",
				"000.000.000.000",
				"any name",
				"any segment",
				AddressMock.createEntity()
			);
			company.setClient(ClientMock.createEntity());
			return company;
		}

	public static CreateCompanyDto createDto() {
		return new CreateCompanyDto(
			"00.000.000/0000-00",
			"000.000.000.000",
			"any name",
			"any segment",
			AddressMock.createDto()
		);
	}

	public static UpdateCompanyDto updateDto() {
		return new UpdateCompanyDto(
			"any name updated",
			"any segment updated"
		);
	}
}
