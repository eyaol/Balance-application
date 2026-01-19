package com.br.itau.desafio.common.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;

/**
 * Entidade que representa o saldo de uma conta no DynamoDB.
 * Utiliza anotações do AWS SDK para mapeamento de atributos.
 *
 * @since 2026-01-18
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class BalanceEntity {

    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("account_id")})
    private String accountId;

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("owner_id")})
    private String ownerId;

    @Getter(onMethod_ = @DynamoDbAttribute("amount"))
    private BigDecimal amount;

    @Getter(onMethod_ = @DynamoDbAttribute("currency"))
    private String currency;

    @Getter(onMethod_ = @DynamoDbAttribute("updated_at"))
    private long updatedAt;

    @Getter(onMethod_ = @DynamoDbAttribute("transaction_id"))
    private String transactionId;

    /**
     * Retorna o TableSchema para mapeamento da entidade no DynamoDB.
     * @return TableSchema de BalanceEntity
     */
    public static TableSchema<BalanceEntity> getTableSchema() {
        return TableSchema.fromBean(BalanceEntity.class);
    }
}
