package com.br.itau.desafio.application.usecase;

import com.br.itau.desafio.adapters.inbound.message.dto.MessageBodyDTO;
import com.br.itau.desafio.application.mapper.BalanceMapper;
import com.br.itau.desafio.application.port.outbound.DynamoDbRepository;
import com.br.itau.desafio.common.entity.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateBalanceUseCase {

    private final DynamoDbRepository dynamoDbRepository;

    public void updateBalance(MessageBodyDTO messageBodyDTO) {
        BalanceEntity entity = BalanceMapper.mapToEntity(messageBodyDTO);
        dynamoDbRepository.saveBalance(entity);
    }

}
