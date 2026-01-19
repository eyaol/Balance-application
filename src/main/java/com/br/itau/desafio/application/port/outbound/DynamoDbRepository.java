package com.br.itau.desafio.application.port.outbound;

import com.br.itau.desafio.common.entity.BalanceEntity;

/**
 * Porta de saída para operações com o DynamoDB.
 * Define métodos para persistência e consulta de saldo.
 *
 * @since 2026-01-18
 */
public interface DynamoDbRepository {
    /**
     * Salva ou atualiza uma entidade de saldo.
     * @param entity entidade BalanceEntity a ser salva
     */
    void saveBalance(BalanceEntity entity);
    /**
     * Busca o saldo pelo accountId.
     * @param accountId identificador da conta
     * @return entidade BalanceEntity correspondente
     */
    BalanceEntity getBalanceByAccountId(String accountId);
}
