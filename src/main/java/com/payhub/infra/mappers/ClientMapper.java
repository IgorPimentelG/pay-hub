package com.payhub.infra.mappers;

import com.payhub.domain.entities.Client;
import com.payhub.infra.dtos.client.CreateClientDto;
import com.payhub.infra.dtos.client.UpdateClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "company", ignore = true)
	Client create(CreateClientDto source);

	void update(UpdateClientDto source, @MappingTarget Client target);
}
