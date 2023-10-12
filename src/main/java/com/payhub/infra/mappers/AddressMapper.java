package com.payhub.infra.mappers;

import com.payhub.domain.entities.Address;
import com.payhub.infra.dtos.client.CreateAddressDto;
import com.payhub.infra.dtos.client.UpdateAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

	@Mapping(target = "id", ignore = true)
	Address create(CreateAddressDto source);

	void update(UpdateAddressDto source, @MappingTarget Address target);
}
