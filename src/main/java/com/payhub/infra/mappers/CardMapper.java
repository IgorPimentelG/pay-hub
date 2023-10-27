package com.payhub.infra.mappers;

import com.payhub.domain.entities.Card;
import com.payhub.infra.dtos.transaction.CardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {

	CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

	@Mapping(target = "id", ignore = true)
	Card create(CardDto source);
}
