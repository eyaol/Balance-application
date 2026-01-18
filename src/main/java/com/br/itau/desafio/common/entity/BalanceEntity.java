package com.br.itau.desafio.common.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;

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

    public static TableSchema<BalanceEntity> getTableSchema() {
        return TableSchema.fromBean(BalanceEntity.class);
    }
}
