package com.br.itau.desafio.application.port.outbound;

import com.br.itau.desafio.common.entity.BalanceEntity;

public interface DynamoDbRepository {
    void saveBalance(BalanceEntity entity);
    BalanceEntity getBalanceByAccountId(String accountId);
}
