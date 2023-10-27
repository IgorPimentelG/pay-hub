package com.payhub.infra.mappers;

import com.payhub.domain.entities.Transaction;
import com.payhub.infra.dtos.transaction.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

	TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "paymentMethod", ignore = true)
	Transaction create(TransactionDto source);
}
