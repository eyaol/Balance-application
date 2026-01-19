package com.br.itau.desafio.application.usecase;

import com.br.itau.desafio.application.port.outbound.DynamoDbRepository;
import com.br.itau.desafio.common.entity.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Caso de uso responsável por recuperar o saldo de uma conta.
 * Utiliza o repositório DynamoDbRepository para acesso aos dados.
 *
 * @since 2026-01-18
 */
@RequiredArgsConstructor
@Component
public class GetBalanceUseCase {

    private final DynamoDbRepository dynamoDbRepository;

    /**
     * Recupera o saldo de uma conta pelo accountId.
     * @param accountId identificador da conta
     * @return entidade BalanceEntity correspondente
     */
    public BalanceEntity getBalance(String accountId) {
        return dynamoDbRepository.getBalanceByAccountId(accountId);
    }

}
