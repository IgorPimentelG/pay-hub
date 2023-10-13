package com.payhub.infra.mocks;

import com.payhub.domain.entities.Company;
import com.payhub.infra.dtos.client.CreateCompanyDto;
import com.payhub.infra.dtos.client.UpdateCompanyDto;

public abstract class CompanyMock {

		public static Company createEntity() {
			return new Company(
				"00.000.000/0000-00",
				"000.000.000.000",
				"any name",
				"any segment",
				AddressMock.createEntity()
			);
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
			"00.000.000/0000-00",
			"000.000.000.000",
			"any name updated",
			"any segment updated"
		);
	}
}
