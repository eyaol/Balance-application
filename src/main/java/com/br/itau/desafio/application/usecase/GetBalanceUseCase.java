package com.br.itau.desafio.application.usecase;

import com.br.itau.desafio.application.port.outbound.DynamoDbRepository;
import com.br.itau.desafio.common.entity.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetBalanceUseCase {

    private final DynamoDbRepository dynamoDbRepository;

    public BalanceEntity getBalance(String accountId) {
        return dynamoDbRepository.getBalanceByAccountId(accountId);
    }

}
