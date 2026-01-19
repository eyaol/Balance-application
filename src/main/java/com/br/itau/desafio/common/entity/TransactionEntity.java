package com.br.itau.desafio.common.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * Entidade que representa uma transação financeira no DynamoDB.
 * Utiliza anotações do AWS SDK para mapeamento de atributos.
 *
 * @since 2026-01-18
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class TransactionEntity {

    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("transaction_id")})
    private String transactionId;

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("account_id")})
    private String accountId;



}
